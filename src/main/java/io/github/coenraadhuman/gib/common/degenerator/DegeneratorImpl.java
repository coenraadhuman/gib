package io.github.coenraadhuman.gib.common.degenerator;

import io.github.coenraadhuman.gib.common.domain.model.DirtyCommit;
import io.github.coenraadhuman.gib.common.domain.model.common.CommitComponents;
import io.github.coenraadhuman.gib.common.utility.ValidConventionalCommitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class DegeneratorImpl implements Degenerator {

  // Todo: fix bug found where an exclamation mark causes the major version to be incremented twice.

  @Override
  public CommitComponents degenerate(DirtyCommit commit) {

    return isValidCommit(commit) ? CommitComponents.builder()
                                           .type(getCommitType(commit))
                                           .exclamation(getCommitExclamation(commit))
                                           .scope(getCommitScope(commit))
                                           .colon(getCommitColon(commit))
                                           .whitespace(getCommitWhitespace(commit))
                                           .description(getCommitDescription(commit))
                                           .body(getCommitFooter(commit))
                                           .build()
                   : CommitComponents
                             .builder()
                             .type("")
                             .exclamation(false)
                             .scope("")
                             .colon(false)
                             .whitespace(false)
                             .description("")
                             .body(Collections.emptyList())
                             .build();
  }

  private boolean isValidCommit(DirtyCommit commit) {
    return hasValidColonFormat(commit) && isValidCommitMessage(commit);
  }

  private boolean isValidCommitMessage(final DirtyCommit commit) {
    return ValidConventionalCommitUtil.isValid(commit.getMessage());
  }

  private String getCommitType(DirtyCommit commit) {
    var preColon = getPreColon(commit);
    return hasValidColonFormat(commit)
                   ? preColon[0].split("!")[0].split("\\(")[0]
                   : "";
  }

  private List<String> getCommitFooter(DirtyCommit commit) {
    return Objects.nonNull(commit.getFooters())
                   ? commit.getFooters()
                   : Collections.emptyList();
  }

  private String[] getPreColon(final DirtyCommit commit) {
    return commit.getMessage().split(":");
  }

  private boolean getCommitExclamation(DirtyCommit commit) {
    var preColon = getPreColon(commit);
    return hasValidColonFormat(commit) && preColon[0].contains("!");
  }

  private String getCommitScope(DirtyCommit commit) {
    var preColon = getPreColon(commit)[0];
    if (preColon.contains("(") && preColon.contains(")")) {
      return preColon.substring(preColon.indexOf("(") + 1, preColon.indexOf(")"));
    }
    return "";
  }

  private boolean getCommitColon(DirtyCommit commit) {
    return hasValidColonFormat(commit);
  }

  private boolean getCommitWhitespace(DirtyCommit commit) {
    return hasValidColonFormat(commit) && getPreColon(commit)[1].indexOf(" ") == 0;
  }

  private String getCommitDescription(DirtyCommit commit) {
    return hasValidColonFormat(commit) ? getPreColon(commit)[1].trim() : "";
  }

  private boolean hasValidColonFormat(DirtyCommit commit) {
    return getPreColon(commit).length >= 2;
  }

}
