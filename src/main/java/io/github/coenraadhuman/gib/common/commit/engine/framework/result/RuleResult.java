package io.github.coenraadhuman.gib.common.commit.engine.framework.result;

import io.github.coenraadhuman.gib.common.commit.engine.framework.enums.RuleStatusEnum;
import lombok.Builder;
import lombok.Setter;

@Builder
public class RuleResult {

  private RuleStatusEnum status;

  @Setter
  private String ruleName;

  public boolean isAppliedOrValid() {
    return status.equals(RuleStatusEnum.APPLIED) || status.equals(RuleStatusEnum.VALID);
  }

  public boolean isNotApplicable() {
    return status.equals(RuleStatusEnum.NOT_APPLICABLE);
  }

  public boolean isInvalid() {
    return status.equals(RuleStatusEnum.INVALID);
  }

}
