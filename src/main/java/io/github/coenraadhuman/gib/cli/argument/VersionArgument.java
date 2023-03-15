package io.github.coenraadhuman.gib.cli.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VersionArgument extends AbstractArgument {

  /**
   * Specify the path of the git project.
   */
  private String path;

  /**
   * Bump current project version with a major increment.
   */
  private boolean major;

  /**
   * Bump current project version with a minor increment.
   */
  private boolean minor;

  /**
   * Bump current project version with a patch increment.
   */
  private boolean patch;

}
