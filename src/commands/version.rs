use crate::conventional::create_conventional_commit;
use crate::git::{retrieve_branch_commits, Commit};
use crate::semantic::{add_commit_to_version, add_impact_to_version, Impact, Version};

pub fn run(
    path: Option<String>,
    major: bool,
    minor: bool,
    patch: bool,
    commit_git_hook: Option<String>,
    scope_filter: Option<String>,
) {
    let flag_count: u8 = vec![major, minor, patch]
        .into_iter()
        .map(|flag| if flag { 1 } else { 0 })
        .sum();

    if flag_count > 1 {
        panic!("Only one of the following flags major, minor or patch are allowed at a time")
    }

    let commits: Vec<Commit> = retrieve_branch_commits(path);

    let mut version = Version::new(0, 0, 0);

    for commit in commits.iter().rev() {
        if let Some(ref message) = commit.message {
            version = add_commit_to_version(
                &version,
                create_conventional_commit(message),
                scope_filter.clone(),
            )
        }
    }

    if let Some(message) = commit_git_hook {
        version = add_commit_to_version(
            &version,
            create_conventional_commit(message.as_str()),
            scope_filter.clone(),
        )
    }

    if major {
        version = add_impact_to_version(&version, Impact::Major);
    }

    if minor {
        version = add_impact_to_version(&version, Impact::Minor);
    }

    if patch {
        version = add_impact_to_version(&version, Impact::Patch);
    }

    version.print();
}
