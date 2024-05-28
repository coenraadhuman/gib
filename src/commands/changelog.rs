extern crate inflector;
use inflector::Inflector;

use crate::conventional::create_conventional_commit;
use crate::git::{retrieve_branch_commits, retrieve_tags};
use crate::semantic::{add_commit_to_version, Version};

// Todo: find a nice template engine for Rust to create the changelog document:
// Todo: use map for tag associated with commits on current branch:
pub fn run(path: Option<String>, _commit_git_hook: Option<String>, scope_filter: Option<String>) {
    let commits = retrieve_branch_commits(path.clone());
    let tags = retrieve_tags(path.clone());

    let mut version = Version::new(0, 0, 0);
    let mut past_tag_version = Version::new(0, 0, 0);
    let mut simple_changelog = "".to_owned();
    let mut unreleased_count: usize = 0;

    simple_changelog.insert_str(0, "</table>\n");
    simple_changelog.insert_str(0, "  </tbody>\n");

    for commit in commits.iter().rev() {
        match commit.message {
            Some(ref message) => {
                version = add_commit_to_version(&version, create_conventional_commit(message), scope_filter.clone());

                match create_conventional_commit(message) {
                    Some(conventional_commit) => {
                        simple_changelog.insert_str(0, "    </tr>\n");
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", commit.committer.to_changelog_string()));
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", commit.author.to_changelog_string()));
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", if conventional_commit.is_deprecrated { 'X' } else { ' ' }));
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", if conventional_commit.is_breaking { 'X' } else { ' ' }));
                        simple_changelog.insert_str(0, &format!("      <td>{}.</td>\n", conventional_commit.commit_description.to_sentence_case()));
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", conventional_commit.commit_type));
                        simple_changelog.insert_str(0, &format!("      <td>{}</td>\n", version.format()));
                        simple_changelog.insert_str(0, "    <tr>\n");
                    },
                    None => (),
                }

                let found_version_as_tag = tags.clone()
                    .into_iter()
                    .filter(|tag| tag.contains(version.format().as_str()))
                    .count() > 0;

                let version_updated_since_previous_releate = !past_tag_version.equal(&version);

                if found_version_as_tag && version_updated_since_previous_releate && unreleased_count != 0 {
                    simple_changelog.insert_str(0, "    </tr>\n");
                    simple_changelog.insert_str(0, &format!("      <td colspan=\"7\"><em><strong>Release: {}</strong></em></td>\n", version.format()));
                    simple_changelog.insert_str(0, "    <tr>\n");

                    past_tag_version = version.clone();
                    unreleased_count = 0;
                } else {
                    unreleased_count = unreleased_count + 1;
                }
            },
            None => {},
        }
    }

    if unreleased_count != 0 {
        simple_changelog.insert_str(0, "    </tr>\n");
        simple_changelog.insert_str(0, "      <td colspan=\"7\"><em><strong>Unreleased</strong></em>\n");
        simple_changelog.insert_str(0, "    <tr>\n");
    }

    simple_changelog.insert_str(0, "  <tbody>\n");
    simple_changelog.insert_str(0, "  </thead>\n");
    simple_changelog.insert_str(0, "    </tr>\n");
    simple_changelog.insert_str(0, "      <th>Committer</th>\n");
    simple_changelog.insert_str(0, "      <th>Author</th>\n");
    simple_changelog.insert_str(0, "      <th>Deprecation</th>\n");
    simple_changelog.insert_str(0, "      <th>Breaking Change</th>\n");
    simple_changelog.insert_str(0, "      <th>Description</th>\n");
    simple_changelog.insert_str(0, "      <th>Commit Type</th>\n");
    simple_changelog.insert_str(0, "      <th>Version</th>\n");
    simple_changelog.insert_str(0, "    <tr>\n");
    simple_changelog.insert_str(0, "  <thead>\n");
    simple_changelog.insert_str(0, "<table>\n");
    simple_changelog.insert_str(0, "# Changelog\n\n");

    print!("{}", simple_changelog);
}