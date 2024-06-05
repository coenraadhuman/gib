use std::env;
use git2::Repository;
use indexmap::IndexMap;

pub struct Author {

    pub name: Option<String>,
    pub email: Option<String>

}

impl Author {

    pub fn to_changelog_string(&self) -> String {
        let name = match self.name {
            Some(ref value) => value,
            None => "Not Available",
        };

        let email = match self.email {
            Some(ref value) => value,
            None => return format!("{}", name),
        };

        format!("<a href=\"mailto:{}\">{}</a>", email, name)
    }

}

pub struct Committer {

    pub name: Option<String>,
    pub email: Option<String>

}

impl Committer {

    pub fn to_changelog_string(&self) -> String {
        let name = match self.name {
            Some(ref value) => value,
            None => "Not Available",
        };

        let email = match self.email {
            Some(ref value) => value,
            None => return format!("{}", name),
        };

        format!("<a href=\"mailto:{}\">{}</a>", email, name)
    }

}

pub struct Commit {

    pub message: Option<String>,
    pub author: Author,
    pub committer: Committer,
    pub oid: String

}

impl Commit {

}

fn retrieve_git_repository(path: String) -> Repository {
    match Repository::open(path) {
        Ok(repository) => repository,
        Err(_) => panic!("Couldn't open repository"),
    }
}

fn determine_path(path: Option<String>) -> String {
    match path {
        Some(path) => path,
        None => {
            match env::current_dir() {
                Ok(path) => match path.into_os_string().into_string() {
                    Ok(auto_path) => auto_path,
                    Err(_) => panic!("Couldn't determine current directory for repository"),
                },
                Err(_) => panic!("System can't find current directory"),
            }
        },
    }
}

pub fn retrieve_commit_tag_map(path: Option<String>) -> Option<IndexMap<String, String>> {
    // Determine repository path:
    let found_path = determine_path(path);

    // Open repository:
    let repo = retrieve_git_repository(found_path);

    // Vector for keeping tags and commit associated:
    let mut tags: Vec<(git2::Commit, String)> = Vec::new();

    // Retrieve tags from repository:
    let tag_names = match repo.tag_names(None) {
        Ok(tags) => tags,
        Err(_) => panic!("Could not retrieve tag names"),
    };

    if tag_names.len() == 0 {
        return None;
    }

    // For each tag find commit:
    for name in tag_names.iter().flatten().map(String::from) {
        // Retrieve git object found for tag:
        let git_object = match repo.revparse_single(&name) {
            Ok(object) => object,
            Err(_) => panic!("Could not retrieve git object for processing"),
        };
        // See if git object is commit:
        if let Ok(commit) = git_object.clone().into_commit() {
            tags.push((commit, name))

        // If it is not commit, it might be a tag, ignore git tree and blobs:
        } else if let Some(tag) = git_object.as_tag() {

            // Find commit for tag object:
            if let Some(commit) = tag.target().ok().and_then(|target| target.into_commit().ok()) {
                tags.push((commit, name));
            }
        }
    }

    return Some(tags
        .into_iter()
        .map(|(commit, tag)| (commit.id().to_string(), tag))
        .collect());
}


pub fn retrieve_branch_commits(path: Option<String>) -> Vec<Commit> {
    let found_path = determine_path(path);
    let repo = retrieve_git_repository(found_path);

    let mut revwalk = match repo.revwalk() {
        Ok(found_revwalk) => found_revwalk,
        Err(_) => panic!("Couldn't retrieve revwalk for branch"),
    };

    match revwalk.push_head() {
        Ok(_) => {},
        Err(_) => panic!("Couldn't push head"),
    };

    return revwalk.map(|step| -> Commit {
        match step {
            Ok(oid) => {
                match repo.find_commit(oid) {
                    Ok(commit) => {
                        let message = match commit.message() {
                            Some(value) => Option::Some(value.to_string()),
                            None => Option::None,
                        };

                        let author = Author {
                            name: match commit.author().name() {
                                Some(value) => Some(value.to_string()),
                                None => None,
                            },
                            email: match commit.author().email() {
                                Some(value) => Some(value.to_string()),
                                None => None,
                            },
                        };

                        let committer = Committer {
                            name: match commit.committer().name() {
                                Some(value) => Some(value.to_string()),
                                None => None,
                            },
                            email: match commit.committer().email() {
                                Some(value) => Some(value.to_string()),
                                None => None,
                            },
                        };

                        Commit { message, author, committer, oid: oid.to_string() }
                    },
                    Err(_) => panic!("Could not retrieve oid of commit"),
                }
            },
            Err(_) => panic!("Could not retrieve commit from oid"),
        }
    }).collect();
}