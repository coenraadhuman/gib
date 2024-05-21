use std::env;

use git2::Repository;

pub struct Commit {

    pub message: Option<String>

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

                        Commit { message }
                    },
                    Err(_) => panic!("Could not retrieve oid of commit"),
                }
            },
            Err(_) => panic!("Could not retrieve commit from oid"),
        }
    }).collect();
}