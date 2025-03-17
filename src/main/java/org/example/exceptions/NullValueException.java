package org.example.exceptions;

public class NullValueException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Какое-то из полей нулевое. Так не должно быть";
    }
}
