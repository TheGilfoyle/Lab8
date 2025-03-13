package org.example.exceptions;

public class IncorrectArgsNumber extends RuntimeException {
  public IncorrectArgsNumber(String message) {
    super(message);
  }
}
