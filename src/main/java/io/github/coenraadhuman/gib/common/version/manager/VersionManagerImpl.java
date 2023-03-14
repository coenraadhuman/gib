package io.github.coenraadhuman.gib.common.version.manager;

import io.github.coenraadhuman.gib.common.domain.DomainFactory;
import io.github.coenraadhuman.gib.common.domain.model.Commit;
import io.github.coenraadhuman.gib.common.domain.model.ProjectData;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;
import org.springframework.stereotype.Component;

@Component
public class VersionManagerImpl implements VersionManager {

  private final Version finalProjectVersion = DomainFactory.getVersion();

  @Override
  public Version calculateProjectVersion(ProjectData projectData) {
    for (var commit : projectData.getCommits()) {
      addVersionToProject(normaliseCommit(commit), finalProjectVersion);
    }
    projectData.setProjectVersion(finalProjectVersion);
    return projectData.getProjectVersion();
  }

  @Override
  public Version calculateProjectVersion(ProjectData projectData, Version version) {
    for (var commit : projectData.getCommits()) {
      addVersionToProject(normaliseCommit(commit), version);
    }
    projectData.setProjectVersion(version);
    return projectData.getProjectVersion();
  }

  private Version normaliseCommit(Commit commit) {
    // Todo: recheck this, one thing noted is that version can't be less 0

    var commitVersion = commit.getDirtyVersion();
    commitVersion.setMajor(commitVersion.getMajor() >= 1 ? 1 : 0);
    commitVersion.setMinor(commitVersion.getMinor() >= 1
                                   && commitVersion.getMajor() <= 0 ? 1 : 0);
    commitVersion.setPatch(commitVersion.getPatch() >= 1
                                   && commitVersion.getMajor() <= 0
                                   && commitVersion.getMinor() <= 0 ? 1 : 0);
    return commitVersion;
  }

  private void addVersionToProject(Version commitVersion, Version projectVersion) {
    if (commitVersion.getMajor() == 1) {
      projectVersion.setMajor(projectVersion.getMajor()
                                      + commitVersion.getMajor());
      projectVersion.setMinor(0);
      projectVersion.setPatch(0);
    } else if (commitVersion.getMinor() == 1) {
      projectVersion.setMinor(projectVersion.getMinor()
                                      + commitVersion.getMinor());
      projectVersion.setPatch(0);
    } else if (commitVersion.getPatch() == 1) {
      projectVersion.setPatch(projectVersion.getPatch()
                                      + commitVersion.getPatch());
    }
  }

}
