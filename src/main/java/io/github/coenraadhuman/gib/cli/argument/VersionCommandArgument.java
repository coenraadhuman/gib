package io.github.coenraadhuman.gib.cli.argument;

import io.github.coenraadhuman.clap.CommandArgument;
import io.github.coenraadhuman.clap.CommandArgumentProcessor;
import io.github.coenraadhuman.clap.Option;

@CommandArgument(input = "version")
public interface VersionCommandArgument extends CommandArgumentProcessor {

  @Option(
          description = "Specify the path of the git project.",
          shortInput = "-pa",
          longInput = "--path",
          providesValue = true // impact what methods we use to find the option
  )
  String path();

  @Option(
          description = "Bump current project version with a major increment.",
          shortInput = "-m",
          longInput = "--major",
          providesValue = false
  )
  boolean major();

  @Option(
          description = "Bump current project version with a minor increment.",
          shortInput = "-mi",
          longInput = "--minor",
          providesValue = false
  )
  boolean minor();

  @Option(
          description = "Bump current project version with a patch increment.",
          shortInput = "-p",
          longInput = "--patch",
          providesValue = false
  )
  boolean patch();

  @Option(
          description = "Mechanism to provide the latest commit made to be included in project version calculation.",
          shortInput = "-c",
          longInput = "--commit-git-hook",
          providesValue = true
  )
  String gitHookCommit();

}
