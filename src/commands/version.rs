use git2::{Commit, Repository};
use std::env;
use crate::version;
use crate::version::Version;

pub fn run(path: Option<String>, major:bool, minor: bool, patch: bool, commit_git_hook: Option<String>, scope_filter: Option<String>) {
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

    let repo = match Repository::open(found_path) {
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

    let commits: Vec<Commit> = revwalk.map(|step| {
        let oid = step.unwrap();

        repo.find_commit(oid).unwrap()
    }).collect();

    let mut version = Version { major: 0, minor: 0 , patch: 0 };

    for commit in commits.iter().rev() {
        match commit.message() {
            Some(message) => {
                version = version::add_commit_to_version(version, message, scope_filter.clone())
            },
            None => {},
        }
    }

    match commit_git_hook {
        Some(message) => {
            version = version::add_commit_to_version(version, message.as_str(), scope_filter.clone())
        },
        None => {},
    }

    if major {
        version = Version::new(version.major + 1, 0, 0);
    }

    if minor {
        version = Version::new(version.major, version.minor + 1, 0);
    }

    if patch {
        version = Version::new(version.major, version.minor, version.patch + 1);
    }

    version.print();
}