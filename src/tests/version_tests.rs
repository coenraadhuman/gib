use crate::semantic::{add_commit_to_version, Version};

#[allow(dead_code)]
fn add_commit_to_version_test(expected_major: u8, expected_minor: u8, expected_patch: u8, commit_message: &str, scope_filter: Option<String>) {
    let version = Version { major: 0, minor: 0 , patch: 0 };

    let result = add_commit_to_version(version, commit_message, scope_filter);
    assert_eq!(result.major, expected_major, "Major match");
    assert_eq!(result.minor, expected_minor, "Minor match");
    assert_eq!(result.patch, expected_patch, "Patch match");
}

#[test]
fn breaking_change() {
    let commit = "feat(foo): add a nice RegEx to check and parse a commit message that folows

        https://www.conventionalcommits.org/en/v1.0.0

        Hello,

        This is an
        multiline example and very nice

        This may also include colons as long as they are not after a word boundary followed after two line brakes
        Example: foo

        Other example: xyz

        Refs: #42
        BREAKING_CHANGE: Brakes wood
        something #mentions a commit";

    assert_eq!(true, commit.contains("BREAKING_CHANGE"), "Commit contains BREAKING_CHANGE");

    add_commit_to_version_test(1, 0, 0, &commit, Option::None);
}

#[test]
fn exclamation_mark() {
    add_commit_to_version_test(1, 0, 0,
        "feat!: hello",
        Option::None
    );
}

#[test]
fn feat() {
    add_commit_to_version_test(0, 1, 0,
        "feat: hello",
        Option::None
    );
}

#[test]
fn feat_with_scope() {
    add_commit_to_version_test(0, 1, 0,
        "feat(something): hello",
        Option::None
    );
}

#[test]
fn feat_with_scope_and_filter() {
    add_commit_to_version_test(0, 1, 0,
        "feat(something): hello",
        Option::Some(String::from(r"^something$"))
    );
}

#[test]
fn refactor() {
    add_commit_to_version_test(0, 0, 1,
        "refactor: hello",
        Option::None
    );
}

#[test]
fn refactor_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "refactor(something): hello",
        Option::None
    );
}

#[test]
fn refactor_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "refactor(hello): hello",
        Option::Some(String::from(r"^hello$"))
    );
}

#[test]
fn perf() {
    add_commit_to_version_test(0, 0, 1,
        "perf: hello",
        Option::None
    );
}

#[test]
fn perf_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "perf(something): hello",
        Option::None
    );
}

#[test]
fn perf_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "perf(something): hello",
        Option::Some(String::from(r"something"))
    );
}

#[test]
fn fix() {
    add_commit_to_version_test(0, 0, 1,
        "fix: hello",
        Option::None
    );
}

#[test]
fn fix_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "fix(something): hello",
        Option::None
    );
}

#[test]
fn fix_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "fix(what): hello",
        Option::Some(String::from(r"^what"))
    );
}

#[test]
fn chore() {
    add_commit_to_version_test(0, 0, 1,
        "chore: hello",
        Option::None
    );
}

#[test]
fn chore_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "chore: hello",
        Option::None
    );
}

#[test]
fn chore_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "chore(blegh): hello",
        Option::Some(String::from(r"blegh"))
    );
}

#[test]
fn revert() {
    add_commit_to_version_test(0, 0, 1,
        "revert: hello",
        Option::None
    );
}

#[test]
fn revert_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "revert(something): hello",
        Option::None
    );
}

#[test]
fn revert_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "revert(something): hello",
        Option::Some(String::from(r"^something$"))
    );
}

#[test]
fn docs() {
    add_commit_to_version_test(0, 0, 1,
        "docs: hello",
        Option::None
    );
}

#[test]
fn docs_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "docs(something): hello",
        Option::None
    );
}

#[test]
fn docs_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "docs(1.2.3): hello",
        Option::Some(String::from(r"[0-9]+.[0-9]+.[0-9]"))
    );
}

#[test]
fn build() {
    add_commit_to_version_test(0, 0, 1,
        "build: hello",
        Option::None
    );
}

#[test]
fn build_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "build(something): hello",
        Option::None
    );
}

#[test]
fn build_with_scope_and_filter() {
    add_commit_to_version_test(0, 0, 1,
        "build(3.22.1): hello",
        Option::Some(String::from(r"^[0-9]+.[0-9]+.[0-9]$"))
    );
}

#[test]
fn unaplicable_commit() {
    add_commit_to_version_test(0, 0, 0,
        "This is a stupid commit",
        Option::None
    );
}

#[test]
fn no_match_scope_filter_spaces() {
    add_commit_to_version_test(0, 0, 0,
        "feat(  something  ): hello",
        Option::Some(String::from(r"^something$"))
    );
}

#[test]
fn no_match_scope_filter_different_word() {
    add_commit_to_version_test(0, 0, 0,
        "feat(  something  ): hello",
        Option::Some(String::from(r"else"))
    );
}
