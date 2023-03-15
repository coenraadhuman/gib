package io.github.coenraadhuman.gib.command.impl;

import io.github.coenraadhuman.gib.cli.argument.AbstractArgument;
import io.github.coenraadhuman.gib.cli.argument.VersionArgument;
import io.github.coenraadhuman.gib.cli.enums.CommandType;
import io.github.coenraadhuman.gib.command.CommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandExecutorImpl implements CommandExecutor {

  private final VersionCommandImpl versionCommand;


  @Override
  public void execute(final AbstractArgument argument, final CommandType type) {
    switch (type) {
      case VERSION -> versionCommand.execute((VersionArgument) argument);
      default -> log.error("No command executed.");
    }
  }

}
