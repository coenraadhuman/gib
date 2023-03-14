package io.github.coenraadhuman.gib.common.commit.engine.framework.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EngineResult {

  private List<RuleResult> ruleHistory;

  public boolean isValid() {
    return !this.isInvalid();
  }

  public boolean isInvalid() {
    return ruleHistory
                   .stream()
                   .anyMatch(RuleResult::isInvalid);
  }

}
