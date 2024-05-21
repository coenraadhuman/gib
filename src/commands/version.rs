use crate::git::{retrieve_branch_commits, Commit};
use crate::semantic::{add_commit_to_version, add_impact_to_version, Version, Impact};

pub fn run(path: Option<String>, major:bool, minor: bool, patch: bool, commit_git_hook: Option<String>, scope_filter: Option<String>) {
    let flag_count: u8 = vec![major, minor, patch].into_iter().map(|flag| if flag { 1 } else { 0 }).sum();

    if flag_count > 1 {
        panic!("Only one of the following flags major, minor or patch are allowed at a time")
    }

    let commits: Vec<Commit> = retrieve_branch_commits(path);

    let mut version = Version::new(0, 0, 0);

    for commit in commits.iter().rev() {
        match commit.message {
            Some(ref message) => {
                version = add_commit_to_version(version, message, scope_filter.clone())
            },
            None => {},
        }
    }

    match commit_git_hook {
        Some(message) => {
            version = add_commit_to_version(version, message.as_str(), scope_filter.clone())
        },
        None => {},
    }

    if major {
        version = add_impact_to_version(version, Impact::MAJOR);
    }

    if minor {
        version = add_impact_to_version(version, Impact::MINOR);
    }

    if patch {
        version = add_impact_to_version(version, Impact::PATCH);
    }

    version.print();
}