package io.github.coenraadhuman.gib.cli.mapper;

import io.github.coenraadhuman.gib.cli.argument.VersionArgument;
import io.github.coenraadhuman.gib.cli.enums.CommandType;
import io.github.coenraadhuman.gib.cli.mapper.common.CommandMapperBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public abstract class VersionCommandMapperBase extends CommandMapperBase {

  @Mapping(target = "path", expression = "java(findArgumentAppender(arguments, \"--path\", \"/\", \".git\"))")
  @Mapping(target = "patch", expression = "java(findArgument(arguments, \"--path\", true, false))")
  @Mapping(target = "minor", expression = "java(findArgument(arguments, \"--minor\", true, false))")
  @Mapping(target = "major", expression = "java(findArgument(arguments, \"--major\", true, false))")
  protected abstract VersionArgument map(String[] arguments, CommandType type);

}
