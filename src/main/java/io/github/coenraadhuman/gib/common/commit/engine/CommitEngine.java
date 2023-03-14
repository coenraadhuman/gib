package io.github.coenraadhuman.gib.common.commit.engine;

import io.github.coenraadhuman.gib.common.commit.engine.framework.result.EngineResult;

public interface CommitEngine<Argument> {

  EngineResult execute(final Argument argument);

}
