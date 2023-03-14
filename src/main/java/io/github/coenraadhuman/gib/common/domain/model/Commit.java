package io.github.coenraadhuman.gib.common.domain.model;

import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import io.github.coenraadhuman.gib.common.domain.model.common.CommitComponents;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Commit {

  private final Version dirtyVersion;
  private final DirtyCommit dirtyCommit;
  private final CommitComponents commitComponents;
  private TypeEnum typeEnum;

}