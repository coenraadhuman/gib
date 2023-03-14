package io.github.coenraadhuman.gib.common.commit.retrieval;

import io.github.coenraadhuman.gib.common.domain.model.DirtyCommit;

import java.util.List;

public interface CommitRetrieval {

  List<DirtyCommit> getCommits();

}
