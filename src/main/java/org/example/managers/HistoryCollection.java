package org.example.managers;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Класс коллекции команд
 */
public class HistoryCollection {
    /**
     * Коллекция команд
     *
     * @param <String> Команды
     */
    Deque<String> historyCollection = new ArrayDeque<>(11);

    /**
     * Добавляет команду в коллекцию
     *
     * @param command
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
     * @return
     */
    public Deque<String> getHistoryCollection() {
        return historyCollection;
    }
}
