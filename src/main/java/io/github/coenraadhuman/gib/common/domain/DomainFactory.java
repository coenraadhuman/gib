package io.github.coenraadhuman.gib.common.domain;

import io.github.coenraadhuman.gib.common.domain.model.ProjectData;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainFactory {

  private static final Supplier<Version> constructorVersion = Version::new;
  private static final Supplier<ProjectData> constructorProjectData = ProjectData::new;

  public static Version getVersion() {
    return constructorVersion.get();
  }

  public static ProjectData getProjectData() {
    return constructorProjectData.get();
  }

}
