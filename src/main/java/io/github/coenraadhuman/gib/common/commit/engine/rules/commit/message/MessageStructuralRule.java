package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.message;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.StructuralValidationRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;

public class MessageStructuralRule extends StructuralValidationRule {

  @Override
  protected RuleResult run(final Commit commit) {
    return RuleResult
                   .builder()
                   .status(RuleStatusEnum.VALID)
                   .build();
  }

}
