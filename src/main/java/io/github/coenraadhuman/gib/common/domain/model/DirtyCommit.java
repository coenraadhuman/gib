package io.github.coenraadhuman.gib.common.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class DirtyCommit {

  private final String message;
  private final List<String> footers;

}
