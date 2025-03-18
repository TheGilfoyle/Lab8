package org.example.exceptions;

/**
 * Класс, описывающий исключение при вводе неверных данных
 */
public class InvalidDataException extends Exception {
    /**
     * Конструктор для создания нового объекта InvalidDataException с указанным сообщением.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
