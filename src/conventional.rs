use std::{fmt::Display, str::FromStr};

use fancy_regex::Regex;
use lazy_static::lazy_static;

pub struct ConventionalCommit {
    pub commit_type: Type,
    pub commit_description: String,
    pub scope: Option<String>,
    pub full_message: String,
    pub is_breaking: bool,
    pub is_deprecrated: bool,
    pub commit_body: Option<String>,
    pub commit_footer: Option<String>,
}

impl ConventionalCommit {
    pub fn new(
        commit_type: Type,
        commit_description: String,
        scope: Option<String>,
        full_message: String,
        is_breaking: bool,
        is_deprecrated: bool,
        commit_body: Option<String>,
        commit_footer: Option<String>,
    ) -> ConventionalCommit {
        ConventionalCommit {
            commit_type,
            commit_description,
            scope,
            full_message,
            is_breaking,
            is_deprecrated,
            commit_body,
            commit_footer,
        }
    }
}

#[derive(Debug, PartialEq)]
pub enum Type {
    Feature,
    Refactor,
    Performance,
    Fix,
    Chore,
    Revert,
    Docs,
    Style,
    Test,
    Build,
    Ci,
}

impl FromStr for Type {
    fn from_str(input: &str) -> Result<Type, Self::Err> {
        match input {
            "feat" => Ok(Type::Feature),
            "refactor" => Ok(Type::Refactor),
            "perf" => Ok(Type::Performance),
            "fix" => Ok(Type::Fix),
            "chore" => Ok(Type::Chore),
            "revert" => Ok(Type::Revert),
            "docs" => Ok(Type::Docs),
            "style" => Ok(Type::Style),
            "test" => Ok(Type::Test),
            "build" => Ok(Type::Build),
            "ci" => Ok(Type::Ci),
            _ => Err(()),
        }
    }

    type Err = ();
}

impl Display for Type {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Type::Feature => write!(f, "Feature"),
            Type::Refactor => write!(f, "Refactor"),
            Type::Performance => write!(f, "Performance"),
            Type::Fix => write!(f, "Fix"),
            Type::Chore => write!(f, "Chore"),
            Type::Revert => write!(f, "Revert"),
            Type::Docs => write!(f, "Documentation"),
            Type::Style => write!(f, "Styling"),
            Type::Test => write!(f, "Testing"),
            Type::Build => write!(f, "Builds"),
            Type::Ci => write!(f, "CI"),
        }
    }
}

pub fn scope_filter_check(scope_regex: Option<String>, scope_value: Option<String>) -> bool {
    match scope_regex {
        // If supplied it needs to match to include commit in calculation;
        Some(ref found_scope_regex) => match Regex::new(found_scope_regex) {
            Ok(scope_filter_regex) => match scope_value {
                Some(value) => scope_filter_regex.is_match(&value).unwrap(),
                None => false,
            },
            Err(_) => panic!("Invalid regex supplied on scope filter"),
        },
        None => {
            // If scope regex is not supplied then include commit in calculation:
            true
        }
    }
}

pub fn create_conventional_commit(commit: &str) -> Option<ConventionalCommit> {
    lazy_static! {
        static ref CONVENTIONAL_COMMIT_REGEX: Regex = Regex::new(r"\A(?:(?P<type>build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test)(?:\((?P<scope>[^\)]+)\))?(?P<breaking>!)?: (?P<description>.+))(?:(?!(?:\n{2}^(?:BREAKING[ -_]CHANGE|[a-zA-Z0-9_\-]+)(?:: | #\b)[^\n:]+(\n*\b)?)+)\n{2}(?P<body>(?:(?!\n{2}(?:^(?:BREAKING[ -]CHANGE|[a-zA-Z0-9_\-]+)(?:: | #\b)[^\n:]+(\n*\b)?)+).|\n+?)+?))?(?:\n{2}(?P<footers>(?:^(?:BREAKING[ -_]CHANGE|[a-zA-Z0-9_\-]+)(?:: | #\b)[^\n:]+(\n*\b)?)+))?(?:\n*)$").unwrap();
    }

    if CONVENTIONAL_COMMIT_REGEX.is_match(commit).unwrap() {
        match CONVENTIONAL_COMMIT_REGEX.captures(commit) {
            Ok(optinionl_captures) => match optinionl_captures {
                Some(captures) => {
                    let commit_type = captures.name("type")?.as_str();
                    let commit_scope = captures.name("scope");
                    let commit_breaking = captures.name("breaking");
                    let commit_description = captures.name("description")?.as_str().trim();
                    let commit_body = captures.name("body");
                    let commit_footer = captures.name("footers");

                    let is_breaking = match commit_breaking {
                        Some(_) => true,
                        None => match commit_body {
                            Some(value) => {
                                value.as_str().contains("BREAKING_CHANGE")
                                    || value.as_str().contains("BREAKING-CHANGE")
                                    || value.as_str().contains("BREAKING CHANGE")
                            }
                            None => match commit_footer {
                                Some(value) => {
                                    value.as_str().contains("BREAKING_CHANGE")
                                        || value.as_str().contains("BREAKING-CHANGE")
                                        || value.as_str().contains("BREAKING CHANGE")
                                }
                                None => false,
                            },
                        },
                    };

                    let is_deprecated = match commit_body {
                        Some(value) => value.as_str().contains("DEPRECATED"),
                        None => match commit_footer {
                            Some(value) => value.as_str().contains("DEPRECATED"),
                            None => false,
                        },
                    };

                    return Some(ConventionalCommit::new(
                        Type::from_str(commit_type).unwrap(),
                        commit_description.to_owned(),
                        commit_scope.map(|value| value.as_str().to_owned()),
                        commit.to_owned(),
                        is_breaking,
                        is_deprecated,
                        commit_body.map(|value| value.as_str().to_owned()),
                        commit_footer.map(|value| value.as_str().to_owned()),
                    ));
                }
                None => panic!("Could not retrieve regex captures for valid commit"),
            },
            Err(_) => panic!("Could not retrieve regex captures for valid commit"),
        }
    }

    Option::None
}
