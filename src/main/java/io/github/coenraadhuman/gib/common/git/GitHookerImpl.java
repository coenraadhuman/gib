package io.github.coenraadhuman.gib.common.git;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
public class GitHookerImpl implements GitHooker {

  private static final String GIT_DIR = ".git";
  private static final String HOOKS_DIR = "hooks";

  @Override
  public Path getGitHookPath() {
    var hooksDirectory = getGitRepository().getDirectory().toPath().resolve(HOOKS_DIR);

    if (!Files.exists(hooksDirectory)) {
      log.debug("Git hook directory doesn't exist, creating directory {}", hooksDirectory);
      try {
        Files.createDirectories(hooksDirectory);
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }

    return hooksDirectory;
  }

  @Override
  public Repository getGitRepository() {

    //    Repository gitRepository;
    //    try {
    //      FileRepositoryBuilder repositoryBuilder =
    //          new FileRepositoryBuilder().findGitDir(// inject base directory);
    //              String gitIndexFileEnvVariable = System.getenv("GIT_INDEX_FILE");
    //      if (StringUtils.isNotBlank(gitIndexFileEnvVariable)) {
    //        repositoryBuilder = repositoryBuilder.setIndexFile(new File(gitIndexFileEnvVariable));
    //      }
    //      gitRepository = repositoryBuilder.build();
    //    } catch (IOException e) {
    //      throw new RuntimeException(
    //          "Could not find the git repository. Run 'git init' if you did not.", e);
    //    }
    //    return gitRepository;

    try {
      return new FileRepository(GIT_DIR);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

  }

}
