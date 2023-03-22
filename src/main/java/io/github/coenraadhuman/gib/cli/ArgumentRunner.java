package io.github.coenraadhuman.gib.cli;

import io.github.coenraadhuman.clap.CommandRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArgumentRunner implements CommandLineRunner {

  private final CommandRunner runner;

  @Override
  public void run(String[] arguments) {
    runner.execute(arguments);
  }

}
