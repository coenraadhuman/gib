#[cfg(test)]

use crate::conventional::{create_conventional_commit, Type};

#[test]
fn feat_with_scope_and_breaking() {
    let result = create_conventional_commit(r"feat(foo)!: add a nice RegEx to check and parse a commit message that folows https://www.conventionalcommits.org/en/v1.0.0");

    assert_eq!(result.is_some(), true);

    let unwrapped_result = result.unwrap();

    assert_eq!(unwrapped_result.commit_type, Type::FEATURE);

    assert_eq!(unwrapped_result.scope.is_some(), true);

    let unwrapped_scope = unwrapped_result.scope.unwrap();

    assert_eq!(unwrapped_scope, "foo");

    assert_eq!(unwrapped_result.is_breaking, true);

    assert_eq!(unwrapped_result.is_deprecrated, false);

    assert_eq!(unwrapped_result.commit_description, "add a nice RegEx to check and parse a commit message that folows https://www.conventionalcommits.org/en/v1.0.0");

    assert_eq!(unwrapped_result.commit_body.is_none(), true);

    assert_eq!(unwrapped_result.commit_footer.is_none(), true);
}

#[test]
fn feat_with_scope_body_and_breaking() {
    let result = create_conventional_commit(r"feat(sdf): add a nice RegEx to check and parse a commit message that folows https://www.conventionalcommits.org/en/v1.0.0

Hello,

This is an
multiline example and very nice

This may also include colons as long as they are not after a word boundary followed after two line brakes
Example: foo

  Other example: xyz

Refs: #42
DEPRECATED
BREAKING-CHANGE: Brakes wood
something #mentions a commit");

    assert_eq!(result.is_some(), true);

    let unwrapped_result = result.unwrap();

    assert_eq!(unwrapped_result.commit_type, Type::FEATURE);

    assert_eq!(unwrapped_result.scope.is_some(), true);

    let unwrapped_scope = unwrapped_result.scope.unwrap();

    assert_eq!(unwrapped_scope, "sdf");

    assert_eq!(unwrapped_result.is_breaking, true);

    assert_eq!(unwrapped_result.is_deprecrated, true);

    assert_eq!(unwrapped_result.commit_description, "add a nice RegEx to check and parse a commit message that folows https://www.conventionalcommits.org/en/v1.0.0");

    assert_eq!(unwrapped_result.commit_body.is_none(), false);

    // Todo: regular expression can't create footer yet (entire text is included in body):
    assert_eq!(unwrapped_result.commit_footer.is_none(), true);
}

#[test]
fn simple_chore() {
    let result = create_conventional_commit(r"chore: something");

    assert_eq!(result.is_some(), true);

    let unwrapped_result = result.unwrap();

    assert_eq!(unwrapped_result.commit_type, Type::CHORE);

    assert_eq!(unwrapped_result.scope.is_some(), false);


    assert_eq!(unwrapped_result.is_breaking, false);

    assert_eq!(unwrapped_result.is_deprecrated, false);

    assert_eq!(unwrapped_result.commit_description, "something");

    assert_eq!(unwrapped_result.commit_body.is_none(), true);

    assert_eq!(unwrapped_result.commit_footer.is_none(), true);
}

#[test]
fn simple_refactor() {
    let result = create_conventional_commit(r"refactor:                 something");

    assert_eq!(result.is_some(), true);

    let unwrapped_result = result.unwrap();

    assert_eq!(unwrapped_result.commit_type, Type::REFACTOR);

    assert_eq!(unwrapped_result.scope.is_some(), false);

    assert_eq!(unwrapped_result.is_breaking, false);

    assert_eq!(unwrapped_result.is_deprecrated, false);

    assert_eq!(unwrapped_result.commit_description, "something");

    assert_eq!(unwrapped_result.commit_body.is_none(), true);

    assert_eq!(unwrapped_result.commit_footer.is_none(), true);
}
