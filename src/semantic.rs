use crate::conventional::{scope_filter_check, ConventionalCommit, Type};

pub struct Version {

    pub major: u8,
    pub minor: u8,
    pub patch: u8

}

impl Version {
    pub fn new(major: u8, minor: u8, patch: u8) -> Version {
        Version { major: major, minor: minor, patch: patch }
    }

    pub fn print(&self) {
        println!("{}.{}.{}", self.major, self.minor, self.patch)
    }
}

pub enum Impact {
    MAJOR,
    MINOR,
    PATCH,
    NONE
}

pub fn add_commit_to_version(version: Version, optional_commit: Option<ConventionalCommit>, scope_filter: Option<String>) -> Version {
    match optional_commit {
        Some(commit) => {

            if scope_filter_check(scope_filter, commit.scope) {
                if commit.is_breaking {
                    return add_impact_to_version(version, Impact::MAJOR);
                }
                let impact = match commit.commit_type {
                    Type::FEATURE => Impact::MINOR,
                    Type::REFACTOR | Type::PERFORMANCE | Type::FIX | Type::CHORE | Type::REVERT | Type::DOCS |  Type::BUILD => Impact::PATCH,
                    Type::STYLE | Type::TEST | Type::CI => Impact::NONE,
                };

                return add_impact_to_version(version, impact)
            }

            return add_impact_to_version(version, Impact::NONE);

        },
        None => add_impact_to_version(version, Impact::NONE),
    }
}

pub fn add_impact_to_version(version: Version, impact: Impact) -> Version {
    match impact {
        Impact::MAJOR => Version::new(version.major + 1, 0, 0),
        Impact::MINOR => Version::new(version.major, version.minor + 1, 0),
        Impact::PATCH => Version::new(version.major, version.minor , version.patch + 1),
        Impact::NONE => Version::new(version.major, version.minor, version.patch),
    }
}