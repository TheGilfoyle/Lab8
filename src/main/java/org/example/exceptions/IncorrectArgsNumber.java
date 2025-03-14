package org.example.exceptions;

public class IncorrectArgsNumber extends RuntimeException{
  private final int number;
  public IncorrectArgsNumber(int number) {
    this.number = number;
  }

  @Override
  public String getMessage() {
    return "Аргументов было введено неправильное количество, ожидалось: " + number;
  }
}