package org.example.exceptions;

public class NullStringException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Вы ввели пустую строку, попробуйте ещё раз!";
    }
}