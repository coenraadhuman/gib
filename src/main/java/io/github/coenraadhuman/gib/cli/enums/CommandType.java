package io.github.coenraadhuman.gib.cli.enums;

import io.github.coenraadhuman.gib.cli.exception.ArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CommandType {

  VERSION("version");

  private final String argument;

  public static CommandType findType(String argument) {
    return Arrays.stream(values())
                   .filter(entry -> entry.getArgument().equals(argument))
                   .findFirst()
                   .orElseThrow(() -> new ArgumentException("Please supply valid argument"));
  }

}
