package org.example.exceptions;

public class InvalidNumberOfParticipantsException extends RuntimeException {
    private final long number;

    public InvalidNumberOfParticipantsException(long number) {
        this.number = number;
    }

    @Override
    public String getMessage() {
        return "Ошибка: количество участников должно быть больше 0, введено: " + number;
    }
}
