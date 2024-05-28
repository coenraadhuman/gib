use std::env;
use git2::Repository;

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

        format!("<a href=\"{}\">{}</a>", email, name)
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

        format!("<a href=\"{}\">{}</a>", email, name)
    }

}

pub struct Commit {

    pub message: Option<String>,
    pub author: Author,
    pub committer: Committer,

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

pub fn retrieve_tags(path: Option<String>) -> Vec<String> {
    let found_path = determine_path(path);
    let repo = retrieve_git_repository(found_path);

    match repo.tag_names(None) {
        Ok(found_tags) => {
            found_tags
            .into_iter()
            .filter(|value| value.is_some())
            .map(|value| value.unwrap().to_owned())
            .collect()
        },
        Err(_) => Vec::new(),
    }
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

                        Commit { message, author, committer }
                    },
                    Err(_) => panic!("Could not retrieve oid of commit"),
                }
            },
            Err(_) => panic!("Could not retrieve commit from oid"),
        }
    }).collect();
}