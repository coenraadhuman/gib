package io.github.coenraadhuman.gib.cli;

import io.github.coenraadhuman.gib.cli.enums.CommandType;
import io.github.coenraadhuman.gib.cli.exception.ArgumentException;
import io.github.coenraadhuman.gib.cli.mapper.AbstractArgumentMapper;
import io.github.coenraadhuman.gib.command.CommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArgumentRunner implements CommandLineRunner {

  private final AbstractArgumentMapper mapper;
  private final CommandExecutor commandExecutor;

  @Override
  public void run(String[] arguments) {
    try {
      var type = determineType(arguments);
      var argument = mapper.map(arguments, type);
      commandExecutor.execute(argument, type);
    } catch (Exception e) {
      log.error(e.getMessage());
      // Todo: print out utility `--help`.
    }
  }

  private CommandType determineType(String[] args) {
    if (args.length == 0) {
      throw new ArgumentException("Please supply valid argument");
    }
    var firstArgument = args[0];
    return CommandType.findType(firstArgument);
  }

}
