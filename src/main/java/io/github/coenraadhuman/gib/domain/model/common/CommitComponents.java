package io.github.coenraadhuman.gib.domain.model.common;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommitComponents {

  private String type;
  private boolean exclamation;
  private String scope;
  private boolean colon;
  private boolean whitespace;
  private String description;
  private List<String> body;

}
