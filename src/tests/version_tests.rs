use crate::version::add_commit_to_version;

#[allow(dead_code)]
fn add_commit_to_version_test(expected_major: u8, expected_minor: u8, expected_patch: u8, commit_message: &str) {
    let result = add_commit_to_version(0, 0, 0, commit_message);
    assert_eq!(result.major, expected_major, "Major match");
    assert_eq!(result.minor, expected_minor, "Minor match");
    assert_eq!(result.patch, expected_patch, "Patch match");
}

#[test]
fn breaking_change() {
    let commit = "
        feat(foo): add a nice RegEx to check and parse a commit message that folows

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

    add_commit_to_version_test(1, 0, 0, &commit);
}

#[test]
fn exclamation_mark() {
    add_commit_to_version_test(1, 0, 0,
        "feat!: hello"
    );
}

#[test]
fn feat() {
    add_commit_to_version_test(0, 1, 0,
        "feat: hello"
    );
}

#[test]
fn feat_with_scope() {
    add_commit_to_version_test(0, 1, 0,
        "feat(something): hello"
    );
}

#[test]
fn refactor() {
    add_commit_to_version_test(0, 0, 1,
        "refactor: hello"
    );
}

#[test]
fn refactor_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "refactor(something): hello"
    );
}

#[test]
fn perf() {
    add_commit_to_version_test(0, 0, 1,
        "perf: hello"
    );
}

#[test]
fn perf_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "perf(something): hello"
    );
}

#[test]
fn fix() {
    add_commit_to_version_test(0, 0, 1,
        "fix: hello"
    );
}

#[test]
fn fix_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "fix(something): hello"
    );
}

#[test]
fn chore() {
    add_commit_to_version_test(0, 0, 1,
        "chore: hello"
    );
}

#[test]
fn chore_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "chore: hello"
    );
}

#[test]
fn revert() {
    add_commit_to_version_test(0, 0, 1,
        "revert: hello"
    );
}

#[test]
fn revert_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "revert(something): hello"
    );
}

#[test]
fn docs() {
    add_commit_to_version_test(0, 0, 1,
        "docs: hello"
    );
}

#[test]
fn docs_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "docs(something): hello"
    );
}

#[test]
fn build() {
    add_commit_to_version_test(0, 0, 1,
        "build: hello"
    );
}

#[test]
fn build_with_scope() {
    add_commit_to_version_test(0, 0, 1,
        "build(something): hello"
    );
}

#[test]
fn unaplicable_commit() {
    add_commit_to_version_test(0, 0, 0,
        "This is a stupid commit"
    );
}