package io.github.coenraadhuman.gib.common.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidConventionalCommitUtil {

  public static boolean isValid(final String message) {
    return Pattern.matches(
            "^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test){1}(\\(.*\\))?(!)?: ([\\w ])+([\\s\\S]*)",
            message);
  }

}
