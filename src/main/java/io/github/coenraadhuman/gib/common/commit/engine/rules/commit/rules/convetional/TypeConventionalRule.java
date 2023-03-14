package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.convetional;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.ConventionalValidationRule;
import io.github.coenraadhuman.gib.common.commit.engine.rules.commit.type.TypeRuleEnum;
import io.github.coenraadhuman.gib.common.domain.model.Commit;

import java.util.Arrays;
import java.util.Optional;


public class TypeConventionalRule extends ConventionalValidationRule {

  @Override
  protected RuleResult run(Commit commit) {

    var ruleStatusEnum = RuleStatusEnum.INVALID;

    Optional<TypeRuleEnum> answer =
            Arrays.stream(TypeRuleEnum.values())
                    .filter(type -> type.evaluate(commit.getCommitComponents()))
                    .findFirst();

    if (answer.isPresent()) {
      commit.setTypeEnum(answer.get().getTypeEnum());
      ruleStatusEnum = RuleStatusEnum.VALID;
    }

    return RuleResult
                   .builder()
                   .status(ruleStatusEnum)
                   .build();
  }

}
