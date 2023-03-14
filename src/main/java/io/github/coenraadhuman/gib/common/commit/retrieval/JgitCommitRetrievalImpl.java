package io.github.coenraadhuman.gib.common.commit.retrieval;

import io.github.coenraadhuman.gib.common.domain.model.DirtyCommit;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FooterLine;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.github.coenraadhuman.gib.common.utility.GitRepositoryFactory.createRepository;
import static io.github.coenraadhuman.gib.common.utility.GitRepositoryFactory.currentWorkingBranch;

@Slf4j
@Component
public class JgitCommitRetrievalImpl implements CommitRetrieval {

  private Git git;
  private Repository repository;

  @Override
  public List<DirtyCommit> getCommits() {
    init();
    return createCommits();
  }

  private void init() {
    repository = createRepository();
    git = new Git(repository);
    log.debug("Branch detected: {}", currentWorkingBranch(repository));
  }

  private List<DirtyCommit> createCommits() {

    List<DirtyCommit> dirtyCommits = getDirtyCommits();
    Collections.reverse(dirtyCommits);
    logDetectedCommits(dirtyCommits);

    return dirtyCommits;
  }

  private void logDetectedCommits(final List<DirtyCommit> dirtyCommits) {
    dirtyCommits.forEach(
            printableCommit -> log.debug(printableCommit.getMessage())
    );
  }

  private List<DirtyCommit> getDirtyCommits() {
    return StreamSupport.stream(getRevCommits().spliterator(), false)
                   .map(this::buildDirtyCommit).collect(Collectors.toList());
  }

  private DirtyCommit buildDirtyCommit(final RevCommit commit) {
    return DirtyCommit
                   .builder()
                   .message(commit.getShortMessage())
                   .footers(getFooters(commit.getFooterLines()))
                   .build();
  }

  private List<String> getFooters(List<FooterLine> footerLines) {
    return footerLines
                   .stream()
                   .map(FooterLine::toString)
                   .collect(Collectors.toList());
  }

  private Iterable<RevCommit> getRevCommits() {
    try {
      return git.log().add(repository.resolve(currentWorkingBranch(repository))).call();
    } catch (IOException | GitAPIException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
