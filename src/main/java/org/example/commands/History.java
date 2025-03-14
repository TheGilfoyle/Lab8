package org.example.commands;

import org.example.Main;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class History extends Command {

    private final Deque<String> history = new LinkedList<>();

    public History() {
        super("history", "вывести последние 11 команд (без их аргументов)",0);
    }

    @Override
    public void execute() {
        super.execute();
        for (String command: Main.hc.getHistoryCollection()){
            System.out.println(command);
        }
    }
}
