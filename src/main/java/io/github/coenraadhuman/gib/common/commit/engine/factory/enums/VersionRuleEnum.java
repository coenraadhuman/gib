package io.github.coenraadhuman.gib.common.commit.engine.factory.enums;

import io.github.coenraadhuman.gib.common.commit.engine.framework.rule.VersionRule;
import io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.version.SemanticVersion;
import io.github.coenraadhuman.gib.common.commit.engine.rules.commit.rules.version.StatefulSemanticVersion;
import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum VersionRuleEnum {

  FEAT_RULE(() -> new SemanticVersion(TypeEnum.FEAT)),
  CHORE_RULE(() -> new SemanticVersion(TypeEnum.CHORE)),
  CI_RULE(() -> new SemanticVersion(TypeEnum.CI)),
  DEPRECATED_RULE(() -> new SemanticVersion(TypeEnum.DEPRECATED)),
  BUILD_RULE(() -> new SemanticVersion(TypeEnum.BUILD)),
  DOCS_RULE(() -> new SemanticVersion(TypeEnum.DOCS)),
  FIX_RULE(() -> new SemanticVersion(TypeEnum.FIX)),
  PERF_RULE(() -> new SemanticVersion(TypeEnum.PERF)),
  REVERT_RULE(() -> new SemanticVersion(TypeEnum.REVERT)),
  STYLE_RULE(() -> new SemanticVersion(TypeEnum.STYLE)),
  TEST_RULE(() -> new SemanticVersion(TypeEnum.TEST)),
  REFACTOR_RULE(() -> new SemanticVersion(TypeEnum.REFACTOR)),


  //Todo Might not need the above enums -- refactor
  BREAKING_CHANGE_RULE(() -> new SemanticVersion(TypeEnum.BREAKING_CHANGE)),
  VERSION_RULE_ENUM(StatefulSemanticVersion::new);

  private final Supplier<VersionRule> constructor;

}
