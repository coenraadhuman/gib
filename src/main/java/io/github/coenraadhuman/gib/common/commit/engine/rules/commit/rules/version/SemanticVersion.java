package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.version;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.VersionRule;
import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import io.github.coenraadhuman.gib.domain.model.Commit;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SemanticVersion extends VersionRule {

  private final TypeEnum type;

  @Override
  public RuleResult execute(Commit commit) {

    commit.getDirtyVersion().addVersion(type);

    return RuleResult
                   .builder()
                   .status(RuleStatusEnum.VALID)
                   .build();
  }

}