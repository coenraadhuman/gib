package io.github.coenraadhuman.gib.common.domain.model;

import io.github.coenraadhuman.gib.common.domain.DomainFactory;
import io.github.coenraadhuman.gib.common.domain.model.common.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectData {

  private Version projectVersion = DomainFactory.getVersion();
  private List<Commit> commits = new ArrayList<>();

}
