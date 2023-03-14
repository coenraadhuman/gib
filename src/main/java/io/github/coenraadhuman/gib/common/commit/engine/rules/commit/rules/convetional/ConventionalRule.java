package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.convetional;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.ConventionalValidationRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;
import io.github.coenraadhuman.gib.common.domain.model.common.CommitComponents;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class ConventionalRule extends ConventionalValidationRule {

  private final Predicate<CommitComponents> predicate;

  @Override
  protected RuleResult run(Commit commit) {

    var ruleStatusEnum = RuleStatusEnum.INVALID;

    if (predicate.test(commit.getCommitComponents())) {
      ruleStatusEnum = RuleStatusEnum.VALID;
    }

    return RuleResult
                   .builder()
                   .status(ruleStatusEnum)
                   .build();
  }

}
