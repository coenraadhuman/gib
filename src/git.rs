use std::env;

use git2::Repository;

pub struct Author {

    pub name: Option<String>,
    pub email: Option<String>

}

impl Author {

    pub fn to_string(&self) -> String {
        let name = match self.name {
            Some(ref value) => value,
            None => "Not Available",
        };

        let email = match self.name {
            Some(ref value) => value,
            None => "not available",
        };

        format!("{} <{}>", name, email)
    }

}

pub struct Committer {

    pub name: Option<String>,
    pub email: Option<String>

}

impl Committer {

    pub fn to_string(&self) -> String {
        let name = match self.name {
            Some(ref value) => value,
            None => "Not Available",
        };

        let email = match self.name {
            Some(ref value) => value,
            None => "not available",
        };

        format!("{} <{}>", name, email)
    }

}

pub struct Commit {

    pub message: Option<String>,
    pub author: Author,
    pub committer: Committer

}

impl Commit {

}

pub fn retrieve_branch_commits(path: Option<String>) -> Vec<Commit> {
    let found_path = match path {
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
    };

    let repo: Repository = match Repository::open(found_path) {
        Ok(repository) => repository,
        Err(_) => panic!("Couldn't open repository"),
    };

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