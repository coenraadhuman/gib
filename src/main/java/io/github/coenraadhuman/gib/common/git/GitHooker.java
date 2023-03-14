package io.github.coenraadhuman.gib.common.git;

import org.eclipse.jgit.lib.Repository;

import java.nio.file.Path;

public interface GitHooker {

  Path getGitHookPath();

  Repository getGitRepository();

}
