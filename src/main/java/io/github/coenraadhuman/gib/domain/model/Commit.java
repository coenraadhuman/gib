package io.github.coenraadhuman.gib.domain.model;

import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import io.github.coenraadhuman.gib.domain.model.common.CommitComponents;
import io.github.coenraadhuman.gib.domain.model.common.Version;
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

  private Version normalisedVersion;

  public Version normalisedVersion() {
    if (normalisedVersion == null) {
      normalisedVersion = new Version(
              dirtyVersion.getMajor() >= 1 ? 1 : 0,
              dirtyVersion.getMinor() >= 1 && dirtyVersion.getMajor() <= 0 ? 1 : 0,
              dirtyVersion.getPatch() >= 1 && dirtyVersion.getMajor() <= 0 && dirtyVersion.getMinor() <= 0 ? 1 : 0
      );
    }
    return normalisedVersion;
  }

}