use regex::Regex;

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

fn version_calculator(version: Version, commit: &str) -> Version {
    let commit_string = commit.to_owned();

    if commit_string.contains("!") || commit_string.contains("BREAKING_CHANGE") {
        return Version::new(version.major + 1, 0, 0);
    }

    if commit_string.contains("feat") {
        return Version::new(version.major, version.minor + 1, 0);
    }

    if commit_string.contains("refactor")
        || commit_string.contains("perf")
        || commit_string.contains("fix")
        || commit_string.contains("chore")
        || commit_string.contains("revert")
        || commit_string.contains("docs")
        || commit_string.contains("build")
    {
        return Version::new(version.major, version.minor , version.patch + 1);
    }

    return Version { major: version.major, minor: version.minor, patch: version.patch };
}

pub fn add_commit_to_version(version: Version, commit: &str, scope_filter: Option<String>) -> Version {

    let conventional_commit_regex = Regex::new(r"^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test){1}(\([\w\.\-\p{Extended_Pictographic}]+\))?(!)?: ([\w \p{Extended_Pictographic}])+([\s\S]*)").unwrap();

    if conventional_commit_regex.is_match(commit) {
        match scope_filter {
            Some(ref found_scope_filter) => {
                let caps = conventional_commit_regex.captures(commit).unwrap();

                match Regex::new(found_scope_filter) {
                    Ok(scope_filter_regex) => {
                        let scope_value = &caps[2][1..&caps[2].len() -1];

                        if scope_filter_regex.is_match(scope_value) {
                            return version_calculator(version, commit);
                        }
                    },
                    Err(_) => panic!("Invalid regex supplied on scope filter"),
                }
            },
            None => {
                return version_calculator(version, commit);
            },
        }
    }

    return Version { major: version.major, minor: version.minor, patch: version.patch };
}