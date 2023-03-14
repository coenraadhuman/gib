package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.structure;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.StructuralValidationRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class CasingRule extends StructuralValidationRule {

  private final Predicate<Commit> predicate;

  @Override
  protected RuleResult run(Commit commit) {

    var status = RuleStatusEnum.INVALID;

    if (predicate.test(commit)) {
      status = RuleStatusEnum.VALID;
    }

    return RuleResult
                   .builder()
                   .status(status)
                   .build();
  }

}