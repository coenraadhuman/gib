package io.github.coenraadhuman.gib.common.commit.engine.rules.commit.type;

import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import io.github.coenraadhuman.gib.domain.model.common.CommitComponents;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.BREAKING_CHANGE;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.BUILD;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.CHORE;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.CI;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.DEPRECATED;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.DOCS;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.FEAT;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.FIX;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.PERF;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.REFACTOR;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.REVERT;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.STYLE;
import static io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum.TEST;


@RequiredArgsConstructor
public enum TypeRuleEnum {

  FEAT_RULE((commitComponent) -> "feat".equals(commitComponent.getType()), FEAT),

  FEATURE_RULE((commitComponent) -> "feature".equals(commitComponent.getType()), FEAT),

  BREAKING_CHANGE_RULE((commitComponent) -> "BREAKING CHANGE".equals(commitComponent.getType()), BREAKING_CHANGE),

  CHORE_RULE((commitComponent) -> "chore".equals(commitComponent.getType()), CHORE),

  CI_RULE((commitComponent) -> "ci".equals(commitComponent.getType()), CI),

  DEPRECATED_RULE((commitComponent) -> "DEPRECATED".equals(commitComponent.getType()), DEPRECATED),

  BUILD_RULE((commitComponent) -> "build".equals(commitComponent.getType()), BUILD),

  DOCS_RULE((commitComponent) -> "docs".equals(commitComponent.getType()), DOCS),

  FIX_RULE((commitComponent) -> "fix".equals(commitComponent.getType()), FIX),

  PERF_RULE((commitComponent) -> "perf".equals(commitComponent.getType()), PERF),

  REVERT_RULE((commitComponent) -> "revert".equals(commitComponent.getType()), REVERT),

  STYLE_RULE((commitComponent) -> "style".equals(commitComponent.getType()), STYLE),

  TEST_RULE((commitComponent) -> "test".equals(commitComponent.getType()), TEST),

  REFACTOR_RULE((commitComponent) -> "refactor".equals(commitComponent.getType()), REFACTOR);

  private final Predicate<CommitComponents> predicate;

  @Getter
  private final TypeEnum typeEnum;

  public boolean evaluate(CommitComponents commitComponent) {
    return predicate.test(commitComponent);
  }

}
