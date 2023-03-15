package io.github.coenraadhuman.gib.git;

import io.github.coenraadhuman.gib.domain.model.Commit;
import org.eclipse.jgit.lib.Repository;

import java.util.List;

public interface GitHelper {

  Repository createRepository(String path);

  String currentWorkingBranch(Repository repository);

  List<Commit> getCurrentBranchCommits(final Repository repository);

}
