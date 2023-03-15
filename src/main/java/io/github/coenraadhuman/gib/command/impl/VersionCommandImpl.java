package io.github.coenraadhuman.gib.command.impl;

import io.github.coenraadhuman.gib.cli.argument.VersionArgument;
import io.github.coenraadhuman.gib.command.common.Command;
import io.github.coenraadhuman.gib.common.commit.engine.CommitEngine;
import io.github.coenraadhuman.gib.common.commit.retrieval.CommitRetrieval;
import io.github.coenraadhuman.gib.common.degenerator.Degenerator;
import io.github.coenraadhuman.gib.common.domain.DomainFactory;
import io.github.coenraadhuman.gib.common.domain.model.Commit;
import io.github.coenraadhuman.gib.common.domain.model.DirtyCommit;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;
import io.github.coenraadhuman.gib.common.version.manager.VersionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class VersionCommandImpl implements Command<VersionArgument> {

  private final Degenerator degenerator;
  private final CommitEngine<Commit> commitEngine;
  private final VersionManager versionManager;
  private final CommitRetrieval commitRetrieval;

  @Override
  public void execute(final VersionArgument argument) {
    var projectVersion = calculate();
    System.out.println(projectVersion.toString());
  }

  private Version calculate() {
    var projectData = DomainFactory.getProjectData();
    var dirtyCommits = new ArrayList<>(commitRetrieval.getCommits());

    for (var dirtyCommit : dirtyCommits) {
      var commit = createCommit(dirtyCommit);
      var result = commitEngine.execute(commit);

      if (result.isValid()) {
        projectData.getCommits().add(commit);
        log.debug("Valid Commit: {}, Commit's dirty version: {}, Project version: {}", dirtyCommit.getMessage(),
                commit.getDirtyVersion().toString(),
                versionManager.calculateProjectVersion(projectData, DomainFactory.getVersion()).toString());
      }
    }

    return versionManager.calculateProjectVersion(projectData);
  }

  private Commit createCommit(DirtyCommit dirtyCommit) {
    var components = degenerator.degenerate(dirtyCommit);

    return Commit
                   .builder()
                   .commitComponents(components)
                   .dirtyVersion(DomainFactory.getVersion())
                   .dirtyCommit(dirtyCommit)
                   .build();
  }


}
