package org.example.exceptions;

/**
 * Класс исключения, который выбрасывается при попытке
 * создать экземпляр с нулевыми полями.
 */
public class NullValueException extends RuntimeException {
    /**
     * Возвращает сообщение об ошибке.
     *
     * @return
     */
    @Override
    public String getMessage() {
        return "Какое-то из полей нулевое. Так не должно быть";
    }
}
