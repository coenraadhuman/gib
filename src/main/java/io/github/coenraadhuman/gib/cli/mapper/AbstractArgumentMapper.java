package io.github.coenraadhuman.gib.cli.mapper;

import io.github.coenraadhuman.gib.cli.argument.AbstractArgument;
import io.github.coenraadhuman.gib.cli.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AbstractArgumentMapper {

  private final VersionCommandMapperBase versionCommandMapper;

  public AbstractArgument map(final String[] arguments, final CommandType type) {
    return switch (type) {
      case VERSION -> versionCommandMapper.map(arguments, type);
    };
  }

}
