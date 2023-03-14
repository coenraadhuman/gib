package io.github.coenraadhuman.gib.common.commit.engine.framework.engine;

import io.github.coenraadhuman.gib.common.commit.engine.CommitEngine;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.EngineResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.result.RuleResult;
import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.common.BasicRule;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicEngine<RuleType extends BasicRule<Argument>, Argument> implements CommitEngine<Argument> {

  private final List<RuleResult> history = new ArrayList<>();

  @Override
  public EngineResult execute(final Argument argument) {
    final List<RuleType> rules = this.assignRules();
    history.clear();

    for (var rule : rules) {
      rule.setEngine(this);
      var result = rule.execute(argument);
      addResultToEngine(result);
      if (result.isInvalid()) {
        break;
      }
    }

    return createEngineResult();
  }

  private EngineResult createEngineResult() {
    var result = new EngineResult();
    result.setRuleHistory(history);

    return result;
  }

  public void addResultToEngine(RuleResult ruleResult) {
    this.history.add(ruleResult);
  }

  public abstract List<RuleType> assignRules();

}
