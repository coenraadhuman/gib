use std::collections::HashMap;

use crate::conventional::{create_conventional_commit, Type};
use crate::git::{retrieve_branch_commits, Commit};
use crate::semantic::{add_commit_to_version, Version};


pub fn run(path: Option<String>, _commit_git_hook: Option<String>, scope_filter: Option<String>) {
    let commits: Vec<Commit> = retrieve_branch_commits(path);

    let mut version = Version::new(0, 0, 0);
    let mut _simple_changelog: HashMap<Type, String> = HashMap::new();

    for commit in commits.iter().rev() {
        match commit.message {
            Some(ref message) => {
                version = add_commit_to_version(version, create_conventional_commit(message), scope_filter.clone())
            },
            None => {},
        }
    }
}