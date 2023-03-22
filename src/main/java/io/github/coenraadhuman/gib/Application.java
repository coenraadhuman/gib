package io.github.coenraadhuman.gib;

import io.github.coenraadhuman.clap.ClapApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@ClapApplication(
        description = "Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history."
)
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
