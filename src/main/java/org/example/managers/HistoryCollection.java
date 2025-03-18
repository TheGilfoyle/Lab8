package org.example.managers;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Класс коллекции команд
 */
public class HistoryCollection {
    /**
     * Коллекция команд
     */
    Deque<String> historyCollection = new ArrayDeque<>(11);

    /**
     * Добавляет команду в коллекцию
     *
     * @param command команда
     */
    public void addCommand(String command) {
        if (historyCollection.size() >= 11) {
            historyCollection.pollFirst();
        }
        historyCollection.addLast(command);
    }

    /**
     * Возвращает коллекцию команд
     *
     * @return Возвращает коллекцию команд
     */
    public Deque<String> getHistoryCollection() {
        return historyCollection;
    }
}
