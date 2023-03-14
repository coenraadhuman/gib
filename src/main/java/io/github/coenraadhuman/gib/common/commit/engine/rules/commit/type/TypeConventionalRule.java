package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.type;


import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.ConventionalValidationRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;

public class TypeConventionalRule extends ConventionalValidationRule {

  @Override
  protected RuleResult run(final Commit commit) {
    return RuleResult
                   .builder()
                   .status(RuleStatusEnum.VALID)
                   .build();
  }

}
