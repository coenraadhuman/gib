package io.github.coenraadhuman.gib.common.commit.engine.framework.rule.common;

import io.github.coenraadhuman.gib.common.commit.engine.framework.engine.BasicEngine;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import lombok.Setter;

public abstract class BasicRule<Argument> {

  @Setter
  protected BasicEngine engine;

  public abstract RuleResult execute(final Argument argument);

}
