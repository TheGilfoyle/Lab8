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
     * Описание команды
     */
    void description();
}
