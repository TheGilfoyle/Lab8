package org.example.managers;

/**
 * Класс для работы с консолью
 */
public class ConsoleManager {
    /**
     * Конструктор класса
     */
    public ConsoleManager() {
    }

    /**
     * Массив токенов
     */
    private String[] tokens;

    /**
     * Получить массив токенов
     *
     * @return Возвращает массив токенов
     */
    public String[] getTokens() {
        return tokens;
    }

    /**
     * Получить токен по индексу
     *
     * @param i индекс токена
     * @return Возвращает токен в виде строки
     */
    public String getToken(int i) {
        return tokens[i];
    }

    /**
     * Установить массив токенов
     *
     * @param tokens токены в виде массива строк
     */
    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}