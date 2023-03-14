package io.github.coenraadhuman.gib.common.commit.engine.framework.rule.common;

import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentRule<Argument> extends BasicRule<Argument> {

  public List<BasicRule<Argument>> childrenRules = new ArrayList<>();

  public ParentRule<Argument> setChildrenRules(List<BasicRule<Argument>> childrenRules) {
    this.childrenRules = childrenRules;
    return this;
  }

  @Override
  public RuleResult execute(final Argument argument) {
    var result = run(argument);

    if (result.isAppliedOrValid()) {
      for (var childRule : childrenRules) {
        childRule.setEngine(engine);
        result = childRule.execute(argument);
        if (result.isInvalid()) {
          break;
        }
      }
    }

    return result;
  }

  protected abstract RuleResult run(final Argument argument);

}
