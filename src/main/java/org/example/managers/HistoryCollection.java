package org.example.managers;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The type History command.
 */
public class HistoryCollection {
    /**
     * The deque of History commands.
     */
    Deque<String> historyCollection = new ArrayDeque<>(11);

    /**
     * Add command to history.
     *
     * @param command the command
     */
    public void addCommand(String command){
        if (historyCollection.size() >= 11) {
            historyCollection.pollFirst();
        }
        historyCollection.addLast(command);
    }

    /**
     * Gets history commands.
     *
     * @return the history commands
     */
    public Deque<String> getHistoryCollection() {
        return historyCollection;
    }
}
