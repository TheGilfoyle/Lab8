package org.example.exceptions;

public class InvalidNumberOfParticipantsException extends RuntimeException {
  public InvalidNumberOfParticipantsException(String message) {
    super(message);
  }
}
