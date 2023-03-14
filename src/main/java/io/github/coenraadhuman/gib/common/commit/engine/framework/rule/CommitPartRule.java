package io.github.coenraadhuman.gib.common.commit.engine.framework.rule;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.common.BasicRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;

import java.util.Objects;

public class CommitPartRule extends BasicRule<Commit> {

  private ConventionalValidationRule conventionalValidationRule;
  private String conventionalValidationRuleName;

  private StructuralValidationRule structuralValidationRule;
  private String structuralValidationRuleName;

  private VersionRule versionRule;
  private String versionRuleName;

  private RuleResult ruleResult = RuleResult
                                          .builder()
                                          .status(RuleStatusEnum.NOT_APPLICABLE)
                                          .build();

  @Override
  public RuleResult execute(final Commit commit) {
    executeAssociatedRule(structuralValidationRuleName, structuralValidationRule, commit);
    executeAssociatedRule(conventionalValidationRuleName, conventionalValidationRule, commit);
    executeAssociatedVersionRule(versionRuleName, versionRule, commit);

    return ruleResult;
  }

  private void executeAssociatedRule(
          final String ruleName,
          final BasicRule<Commit> associatedRule,
          final Commit commit
  ) {
    if (Objects.nonNull(associatedRule) && (ruleResult.isAppliedOrValid() || ruleResult.isNotApplicable())) {
      executeRule(ruleName, associatedRule, commit);
    }
  }

  private void executeAssociatedVersionRule(
          final String ruleName,
          final BasicRule<Commit> associatedRule,
          final Commit commit
  ) {
    if (Objects.nonNull(associatedRule) && ruleResult.isAppliedOrValid()) {
      executeRule(ruleName, associatedRule, commit);
    }
  }

  private void executeRule(String ruleName, BasicRule<Commit> associatedRule, Commit commit) {
    associatedRule.setEngine(engine);
    ruleResult = associatedRule.execute(commit);
    ruleResult.setRuleName(ruleName);
    engine.addResultToEngine(ruleResult);
  }

  public CommitPartRule setConventionalValidationRule(
          final String conventionalValidationRuleName,
          final ConventionalValidationRule conventionalValidationRule
  ) {
    this.conventionalValidationRuleName =
            String.format("Conventional Validation Rule: %s", conventionalValidationRuleName);
    this.conventionalValidationRule = conventionalValidationRule;
    return this;
  }

  public CommitPartRule setStructuralValidationRule(
          final String structuralValidationRuleName,
          final StructuralValidationRule structuralValidationRule
  ) {
    this.structuralValidationRuleName = String.format("Structural Validation Rule: %s", structuralValidationRuleName);
    this.structuralValidationRule = structuralValidationRule;
    return this;
  }

  public CommitPartRule setVersionRule(
          final String versionRuleName,
          final VersionRule versionRule
  ) {
    this.versionRuleName = String.format("Version Rule: %s", versionRuleName);
    this.versionRule = versionRule;
    return this;
  }

}
