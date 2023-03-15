package io.github.coenraadhuman.gib.command;

import io.github.coenraadhuman.gib.cli.argument.AbstractArgument;
import io.github.coenraadhuman.gib.cli.enums.CommandType;

public interface CommandExecutor {

  void execute(final AbstractArgument argument, final CommandType type);

}
