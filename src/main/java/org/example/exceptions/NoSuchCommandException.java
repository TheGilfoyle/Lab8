package org.example.exceptions;

/**
 * Класс, описывающий ошибку при невалидной команде в скрипте.
 */
public class NoSuchCommandException extends Exception {

    /**
     * Возвращает сообщение об ошибке при невалидной команде в скрипте.
     * @return Строка с сообщением об ошибке
     */
    @Override
    public String getMessage() {
        return "Какая-то из команд в скрипте не валидная";
    }
}
