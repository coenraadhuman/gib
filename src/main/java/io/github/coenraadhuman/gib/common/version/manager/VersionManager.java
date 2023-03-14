package io.github.coenraadhuman.gib.common.version.manager;

import io.github.coenraadhuman.gib.common.domain.model.ProjectData;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;

public interface VersionManager {

  Version calculateProjectVersion(ProjectData projectData);

  Version calculateProjectVersion(ProjectData projectData, Version version);

}
