extern crate inflector;
use inflector::Inflector;

use crate::conventional::create_conventional_commit;
use crate::git::{retrieve_branch_commits, Commit};
use crate::semantic::{add_commit_to_version, Version};


pub fn run(path: Option<String>, _commit_git_hook: Option<String>, scope_filter: Option<String>) {
    let commits: Vec<Commit> = retrieve_branch_commits(path);

    let mut version = Version::new(0, 0, 0);

    let mut simple_changelog = "".to_owned();

    for commit in commits.iter().rev() {
        match commit.message {
            Some(ref message) => {
                version = add_commit_to_version(version, create_conventional_commit(message), scope_filter.clone());

                match create_conventional_commit(message) {
                    Some(conventional_commit) => {

                        simple_changelog.insert_str(0,
                            &format!("|{}|{}|{}.|{}|{}|\n",
                                version.format(),
                                conventional_commit.commit_type,
                                conventional_commit.commit_description.to_sentence_case(),
                                if conventional_commit.is_breaking { 'X' } else { ' ' },
                                if conventional_commit.is_deprecrated { 'X' } else { ' ' },
                            ).as_str()
                        );

                    },
                    None => todo!(),
                }
            },
            None => {},
        }
    }

    simple_changelog.insert_str(0, "|---|---|---|---|---|\n");

    simple_changelog.insert_str(0, "|Version|Commit Type|Description|Breaking Change|Deprecation|\n");

    simple_changelog.insert_str(0, &format!("## {}\n\n", version.format()));

    simple_changelog.insert_str(0, "# Changelog\n\n");

    print!("{}", simple_changelog);
}