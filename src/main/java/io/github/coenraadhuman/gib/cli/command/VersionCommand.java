package io.github.coenraadhuman.gib.cli.command;

import io.github.coenraadhuman.clap.Command;
import io.github.coenraadhuman.clap.CommandProcessor;
import io.github.coenraadhuman.gib.cli.argument.VersionCommandArgument;
import io.github.coenraadhuman.gib.common.commit.engine.CommitEngine;
import io.github.coenraadhuman.gib.domain.DomainFactory;
import io.github.coenraadhuman.gib.domain.model.Commit;
import io.github.coenraadhuman.gib.domain.model.ProjectData;
import io.github.coenraadhuman.gib.domain.model.common.Version;
import io.github.coenraadhuman.gib.git.GitHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
@Command(
        argument = VersionCommandArgument.class,
        componentModel = "spring",
        description = "Command to calculate the semantic version based on the conventional commits of the current branch."
)
public final class VersionCommand implements CommandProcessor<VersionCommandArgument> {

  private final CommitEngine<Commit> commitEngine;
  private final GitHelper git;

  @Override
  public void process(VersionCommandArgument commandArgument) {
    var projectData = calculate(commandArgument);
    if (commandArgument.gitHookCommit() != null) {
      addCommitToProjectData(projectData, git.buildGitHookCommit(commandArgument.gitHookCommit()));
    }
    if (commandArgument.major()) {
      addCustomVersionToProject(projectData, Version.builder().major(1).minor(1).patch(0).build());
    }
    if (commandArgument.minor()) {
      addCustomVersionToProject(projectData, Version.builder().major(0).minor(1).patch(0).build());
    }
    if (commandArgument.patch()) {
      addCustomVersionToProject(projectData, Version.builder().major(0).minor(0).patch(1).build());
    }
    System.out.println(projectData.getProjectVersion().toString());
  }

  private ProjectData calculate(final VersionCommandArgument argument) {
    var projectData = DomainFactory.getProjectData();
    var repository = git.createRepository(argument.path());
    var commits = new ArrayList<>(git.getCurrentBranchCommits(repository));

    log.debug(String.format("%-14s | %-15s | %s", "Commit Version", "Project Version", "Commit Message"));
    for (var commit : commits) {
      addCommitToProjectData(projectData, commit);
    }

    return projectData;
  }

  private void addCommitToProjectData(final ProjectData projectData, final Commit commit) {
    var result = commitEngine.execute(commit);

    if (result.isValid()) {
      projectData.getCommits().add(commit);
      projectData.setProjectVersion(null);
      log.debug(String.format("%-14s | %-15s | %s",
              commit.getDirtyVersion().toString(),
              projectData.getProjectVersion(),
              commit.getDirtyCommit().getMessage())
      );
    }
  }

  private void addCustomVersionToProject(ProjectData projectData, Version version) {
    projectData.getCommits().add(
            Commit.builder()
                    .normalisedVersion(version)
                    .build()
    );
    projectData.setProjectVersion(null);
  }

}
