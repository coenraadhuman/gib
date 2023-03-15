package io.github.coenraadhuman.gib.domain.model;

import io.github.coenraadhuman.gib.domain.DomainFactory;
import io.github.coenraadhuman.gib.domain.model.common.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectData {

  private Version projectVersion;
  private List<Commit> commits = new ArrayList<>();

  public Version getProjectVersion() {
    if (projectVersion == null) {
      projectVersion = DomainFactory.getVersion();
      for (var commit : commits) {
        addVersionToProject(commit.normalisedVersion());
      }
    }
    return projectVersion;
  }

  private void addVersionToProject(Version commitVersion) {
    if (commitVersion.getMajor() == 1) {
      projectVersion.setMajor(projectVersion.getMajor() + commitVersion.getMajor());
      projectVersion.setMinor(0);
      projectVersion.setPatch(0);
    } else if (commitVersion.getMinor() == 1) {
      projectVersion.setMinor(projectVersion.getMinor() + commitVersion.getMinor());
      projectVersion.setPatch(0);
    } else if (commitVersion.getPatch() == 1) {
      projectVersion.setPatch(projectVersion.getPatch() + commitVersion.getPatch());
    }
  }

}
