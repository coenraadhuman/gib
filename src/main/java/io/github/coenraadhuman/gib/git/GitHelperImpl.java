package io.github.coenraadhuman.gib.git;

import io.github.coenraadhuman.gib.common.degenerator.Degenerator;
import io.github.coenraadhuman.gib.domain.DomainFactory;
import io.github.coenraadhuman.gib.domain.model.Commit;
import io.github.coenraadhuman.gib.domain.model.DirtyCommit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FooterLine;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHelperImpl implements GitHelper {

  private final Degenerator degenerator;

  @Override
  public Repository createRepository(String path) {
    try {
      if (path == null || StringUtils.containsWhitespace(path)) {
        return new FileRepository(".git");
      } else {
        if (path.endsWith(".git")) {
          return new FileRepository(path);
        } else {
          var isCorrectCharacter = path.charAt(path.length() - 1);
          if ('/' == isCorrectCharacter) {
            return new FileRepository(String.format("%s.git", path));
          } else {
            return new FileRepository(String.format("%s/.git", path));
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public String currentWorkingBranch(Repository repository) {
    try {
      return repository.getFullBranch();
    } catch (Exception e) {
      throw new RuntimeException("Can't retrieve branch name.");
    }
  }

  @Override
  public List<Commit> getCurrentBranchCommits(final Repository repository) {
    var commits = StreamSupport
                          .stream(getRevCommits(repository).spliterator(), false)
                          .map(this::buildDirtyCommit)
                          .map(this::buildCommit)
                          .collect(Collectors.toList());

    Collections.reverse(commits);

    commits.forEach(commit -> log.debug(commit.getDirtyCommit().getMessage()));

    return commits;
  }

  @Override
  public Commit buildGitHookCommit(String gitHookMessage) {
    var dirtyCommit = DirtyCommit
                              .builder()
                              .message(gitHookMessage)
                              .build();
    return buildCommit(dirtyCommit);
  }

  private Commit buildCommit(final DirtyCommit dirtyCommit) {
    return Commit.builder()
                   .commitComponents(degenerator.degenerate(dirtyCommit))
                   .dirtyVersion(DomainFactory.getVersion())
                   .dirtyCommit(dirtyCommit)
                   .build();
  }

  private DirtyCommit buildDirtyCommit(final RevCommit commit) {
    return DirtyCommit
                   .builder()
                   .message(commit.getShortMessage())
                   .footers(commit.getFooterLines()
                                    .stream()
                                    .map(FooterLine::toString)
                                    .collect(Collectors.toList()))
                   .build();
  }

  private Iterable<RevCommit> getRevCommits(Repository repository) {
    try {
      var git = new Git(repository);
      return git.log().add(repository.resolve(currentWorkingBranch(repository))).call();
    } catch (IOException | GitAPIException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
