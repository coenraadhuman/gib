package io.github.coenraadhuman.gib.cli;

import io.github.coenraadhuman.gib.command.Version;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArgumentProcessor implements CommandLineRunner {

  private final Version version;

  @Override
  public void run(String... args) {
    for (var arg : args) {
      if (arg.equals("version")) {
        System.out.println(version.calculate());
      }
    }
  }

}
