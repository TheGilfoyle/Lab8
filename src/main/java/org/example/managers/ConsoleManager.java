package org.example.managers;

/**
 * Класс для работы с консолью
 */
public class ConsoleManager {
    /**
     * Массив токенов
     */
    private String[] tokens;

    /**
     * Получить массив токенов
     *
     * @return
     */
    public String[] getTokens() {
        return tokens;
    }

    /**
     * Получить токен по индексу
     *
     * @param i
     * @return
     */
    public String getToken(int i) {
        return tokens[i];
    }

    /**
     * Установить массив токенов
     *
     * @param tokens
     */
    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}