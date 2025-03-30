package org.example.interfaces;

/**
 * Интерфейс команд для реализации паттерна команда
 */
public interface Commander {
    /**
     * Выполнение команды
     */
    void execute();

    /**
     * Выполнение команды в режиме скрипта
     * @param args аргументы
     */
    void execute(String[] args);

    /**
     * Описание команды
     */
    void description();
}
