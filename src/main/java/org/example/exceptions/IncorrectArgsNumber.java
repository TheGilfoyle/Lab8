package org.example.exceptions;

/**
 * Класс исключения, выбрасываемый в случае, если было введено неправильное количество аргументов
 *
 */
public class IncorrectArgsNumber extends RuntimeException {
    /**
     * Количество аргументов для команды
     */
    private final int number;

    /**
     * Конструктор класса
     *
     * @param number количество аргументов
     */
    public IncorrectArgsNumber(int number) {
        this.number = number;
    }

    /**
     * Возвращает строку с описанием ошибки
     *
     * @return строка с описанием ошибки
     */
    @Override
    public String getMessage() {
        return "Аргументов было введено неправильное количество, ожидалось: " + number;
    }
}

