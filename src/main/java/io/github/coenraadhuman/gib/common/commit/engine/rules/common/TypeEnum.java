package io.github.coenraadhuman.gib.common.commit.engine.rules.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {

  //------Major
  BREAKING_CHANGE(ReleaseEnum.MAJOR),

  //------Minor
  FEAT(ReleaseEnum.MINOR),

  //------Patch
  REFACTOR(ReleaseEnum.PATCH),
  PERF(ReleaseEnum.PATCH),
  FIX(ReleaseEnum.PATCH),
  CHORE(ReleaseEnum.PATCH),
  REVERT(ReleaseEnum.PATCH),
  DOCS(ReleaseEnum.PATCH),
  BUILD(ReleaseEnum.PATCH),

  //------NONE
  CI(ReleaseEnum.NONE),
  STYLE(ReleaseEnum.NONE),
  TEST(ReleaseEnum.NONE),
  DEPRECATED(ReleaseEnum.NONE);

  private final ReleaseEnum release;

}
