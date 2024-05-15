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

pub fn add_commit_to_version(major: u8, minor: u8, patch: u8, commit: &str) -> Version {
    let commit_string = commit.to_owned();

    let conventional_commit_regex = Regex::new(r"^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test){1}(\([\w\.\-\p{Extended_Pictographic}]+\))?(!)?: ([\w \p{Extended_Pictographic}])+([\s\S]*)").unwrap();

    if conventional_commit_regex.is_match(commit) {
        if commit_string.contains("!") || commit_string.contains("BREAKING_CHANGE") {
            return Version::new(major + 1, 0, 0);
        }

        if commit_string.contains("feat") {
            return Version::new(major, minor + 1, 0);
        }

        if commit_string.contains("refactor")
            || commit_string.contains("perf")
            || commit_string.contains("fix")
            || commit_string.contains("chore")
            || commit_string.contains("revert")
            || commit_string.contains("docs")
            || commit_string.contains("build")
        {
            return Version::new(major, minor , patch + 1);
        }
    }

    return Version { major: major, minor: minor, patch: patch };
}