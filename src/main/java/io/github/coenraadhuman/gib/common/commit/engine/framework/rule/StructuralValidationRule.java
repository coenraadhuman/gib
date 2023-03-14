package io.github.coenraadhuman.gib.common.commit.engine.framework.rule;

import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.common.ParentRule;
import io.github.coenraadhuman.gib.common.domain.model.Commit;

public abstract class StructuralValidationRule extends ParentRule<Commit> {

  public StructuralValidationRule addChild(StructuralValidationRule rule) {
    this.childrenRules.add(rule);
    return this;
  }

}
