package io.github.coenraadhuman.gib.cli.mapper.common;

public abstract class CommandMapperBase {

  protected String findArgumentAppender(
          final String[] arguments, final String wantedArgument, final String seperator, final String appendingValue
  ) {
    var foundArgument = findArgument(arguments, wantedArgument);
    if (foundArgument != null) {
      return String.format("%s%s%s", foundArgument, seperator, appendingValue);
    }
    return appendingValue;
  }

  protected <T> T findArgument(
          final String[] arguments, final String wantedArgument, final T returnValue, T defaultValue
  ) {
    var foundArgument = findArgument(arguments, wantedArgument, returnValue);
    if (foundArgument == null) {
      return defaultValue;
    }
    return foundArgument;
  }

  protected <T> T findArgument(final String[] arguments, final String wantedArgument, final T returnValue) {
    var foundArgument = findArgument(arguments, wantedArgument);
    if (foundArgument != null) {
      return returnValue;
    }
    return null;
  }

  protected String findArgument(final String[] argument, final String wantedArgument) {
    for (int i = 1; i < argument.length; i++) {
      if (argument[i].equals(wantedArgument)) {
        return argument[i];
      }
    }
    return null;
  }

}
