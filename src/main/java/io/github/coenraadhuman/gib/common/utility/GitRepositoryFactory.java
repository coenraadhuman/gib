package io.github.coenraadhuman.gib.common.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitRepositoryFactory {

  public static final String GIT = ".git";

  public static Repository createRepository() {
    try {
      return new FileRepository(GIT);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static String currentWorkingBranch(Repository repository) {
    try {
      return repository.getFullBranch();
    } catch (Exception e) {
      throw new RuntimeException("Can't retrieve branch name.");
    }
  }

  public static String currentWorkingBranch() {
    try (Repository repository = createRepository()) {
      return repository.getFullBranch();
    } catch (Exception e) {
      throw new RuntimeException("Can't retrieve branch name.");
    }
  }

}
