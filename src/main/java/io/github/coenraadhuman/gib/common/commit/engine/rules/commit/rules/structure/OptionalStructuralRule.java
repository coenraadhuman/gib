package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.structure;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.StructuralValidationRule;
import io.github.coenraadhuman.gib.domain.model.Commit;
import io.github.coenraadhuman.gib.domain.model.common.CommitComponents;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class OptionalStructuralRule extends StructuralValidationRule {

  private final Predicate<CommitComponents> predicate;

  @Override
  protected RuleResult run(Commit commit) {

    var status = predicate.test(commit.getCommitComponents())
                         ? RuleStatusEnum.VALID
                         : RuleStatusEnum.NOT_APPLICABLE;

    return RuleResult
                   .builder()
                   .status(status)
                   .build();
  }

}