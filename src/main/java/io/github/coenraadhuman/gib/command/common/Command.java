package io.github.coenraadhuman.gib.command.common;

public interface Command<T> {

  void execute(T argument);

}
