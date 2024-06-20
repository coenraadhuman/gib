use crate::conventional::{scope_filter_check, ConventionalCommit, Type};

pub struct Version {
    pub major: u8,
    pub minor: u8,
    pub patch: u8,
}

impl Version {
    pub fn new(major: u8, minor: u8, patch: u8) -> Version {
        Version {
            major,
            minor,
            patch,
        }
    }

    pub fn print(&self) {
        println!("{}", self.format())
    }

    pub fn format(&self) -> String {
        format!("{}.{}.{}", self.major, self.minor, self.patch)
    }
}

pub enum Impact {
    Major,
    Minor,
    Patch,
    None,
}

pub fn add_commit_to_version(
    version: &Version,
    optional_commit: Option<ConventionalCommit>,
    scope_filter: Option<String>,
) -> Version {
    match optional_commit {
        Some(commit) => {
            if scope_filter_check(scope_filter, commit.scope) {
                if commit.is_breaking {
                    return add_impact_to_version(version, Impact::Major);
                }
                let impact = match commit.commit_type {
                    Type::Feature => Impact::Minor,
                    Type::Refactor
                    | Type::Performance
                    | Type::Fix
                    | Type::Chore
                    | Type::Revert
                    | Type::Docs
                    | Type::Build => Impact::Patch,
                    Type::Style | Type::Test | Type::Ci => Impact::None,
                };

                return add_impact_to_version(version, impact);
            }

            add_impact_to_version(version, Impact::None)
        }
        None => add_impact_to_version(version, Impact::None),
    }
}

pub fn add_impact_to_version(version: &Version, impact: Impact) -> Version {
    match impact {
        Impact::Major => Version::new(version.major + 1, 0, 0),
        Impact::Minor => Version::new(version.major, version.minor + 1, 0),
        Impact::Patch => Version::new(version.major, version.minor, version.patch + 1),
        Impact::None => Version::new(version.major, version.minor, version.patch),
    }
}
