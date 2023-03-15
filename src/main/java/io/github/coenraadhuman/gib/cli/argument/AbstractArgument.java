package io.github.coenraadhuman.gib.cli.argument;

import io.github.coenraadhuman.gib.cli.enums.CommandType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractArgument {

  private CommandType type;

}
