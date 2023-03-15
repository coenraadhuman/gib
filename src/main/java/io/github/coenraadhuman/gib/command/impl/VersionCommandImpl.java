package io.github.coenraadhuman.gib.command.impl;

import io.github.coenraadhuman.gib.cli.argument.VersionArgument;
import io.github.coenraadhuman.gib.command.common.Command;
import io.github.coenraadhuman.gib.common.commit.engine.CommitEngine;
import io.github.coenraadhuman.gib.domain.DomainFactory;
import io.github.coenraadhuman.gib.domain.model.Commit;
import io.github.coenraadhuman.gib.domain.model.common.Version;
import io.github.coenraadhuman.gib.git.GitHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class VersionCommandImpl implements Command<VersionArgument> {

  private final CommitEngine<Commit> commitEngine;
  private final GitHelper git;

  @Override
  public void execute(final VersionArgument argument) {
    var projectVersion = calculate(argument);
    System.out.println(projectVersion.toString());
  }

  private Version calculate(final VersionArgument argument) {
    var projectData = DomainFactory.getProjectData();
    var repository = git.createRepository(argument.getPath());
    var commits = new ArrayList<>(git.getCurrentBranchCommits(repository));

    for (var commit : commits) {

      var result = commitEngine.execute(commit);

      if (result.isValid()) {
        projectData.getCommits().add(commit);
        projectData.setProjectVersion(null);
        log.debug("Valid Commit: {}, Commit's dirty version: {}, Project version: {}",
                commit.getDirtyCommit().getMessage(),
                commit.getDirtyVersion().toString(),
                projectData.getProjectVersion()
        );
      }
    }

    return projectData.getProjectVersion();
  }

}
