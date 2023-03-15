package io.github.coenraadhuman.gib.common.degenerator;

import io.github.coenraadhuman.gib.domain.model.DirtyCommit;
import io.github.coenraadhuman.gib.domain.model.common.CommitComponents;

public interface Degenerator {

  CommitComponents degenerate(DirtyCommit commit);

}
