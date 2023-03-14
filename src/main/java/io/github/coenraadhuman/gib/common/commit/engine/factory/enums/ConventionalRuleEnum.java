package io.github.coenraadhuman.gib.common.commit.engine.factory.enums;

import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.ConventionalValidationRule;
import io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.convetional.OptionalConventionalRule;
import io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.convetional.TypeConventionalRule;
import io.github.coenraadhuman.gib.common.domain.model.common.CommitComponents;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum ConventionalRuleEnum {

  TYPE_RULE(TypeConventionalRule::new),

  BREAKING_BODY_RULE(() -> new OptionalConventionalRule(
          (commitComponent) -> commitComponent.getBody() != null && commitComponent.getBody().stream().anyMatch(
                  e -> e.contains("BREAKING CHANGE"))
  )),

  BREAKING_EXCLAMATION_RULE(() -> new OptionalConventionalRule(CommitComponents::isExclamation)
  );

  @Getter
  private final Supplier<ConventionalValidationRule> constructor;

}
