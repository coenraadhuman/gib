use crate::git::{retrieve_branch_commits, Commit};
use crate::semantic;
use crate::semantic::Version;

pub fn run(path: Option<String>, major:bool, minor: bool, patch: bool, commit_git_hook: Option<String>, scope_filter: Option<String>) {
    let commits: Vec<Commit> = retrieve_branch_commits(path);

    let mut version = Version { major: 0, minor: 0 , patch: 0 };

    for commit in commits.iter().rev() {
        match commit.message {
            Some(ref message) => {
                version = semantic::add_commit_to_version(version, message, scope_filter.clone())
            },
            None => {},
        }
    }

    match commit_git_hook {
        Some(message) => {
            version = semantic::add_commit_to_version(version, message.as_str(), scope_filter.clone())
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