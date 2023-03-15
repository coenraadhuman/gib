package io.github.coenraadhuman.gib.common.domain.model.common;

import io.github.coenraadhuman.gib.common.commit.engine.rules.common.ReleaseEnum;
import io.github.coenraadhuman.gib.common.commit.engine.rules.common.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Version {

  private Integer major = 0;
  private Integer minor = 0;
  private Integer patch = 0;

  @Override
  public String toString() {
    return String.format("%d.%d.%d", major, minor, patch);
  }

  public void addVersion(TypeEnum typeEnum) {
    if (ReleaseEnum.MAJOR.equals(typeEnum.getRelease())) {
      this.major += 1;
      this.minor = 0;
      this.patch = 0;
    } else if (ReleaseEnum.MINOR.equals(typeEnum.getRelease())) {
      this.minor += 1;
      this.patch = 0;
    } else if (ReleaseEnum.PATCH.equals(typeEnum.getRelease())) {
      this.patch += 1;
    }
  }

}
