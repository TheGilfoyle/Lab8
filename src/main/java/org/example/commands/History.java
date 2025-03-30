package org.example.commands;

import org.example.Main;

/**
 * Класс команды вывода истории
 */
public class History extends Command {
    /**
     * Конструктор класса команды вывода истории
     */
    public History() {
        super("history", "вывести последние 11 команд (без их аргументов)", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        for (String command : Main.hc.getHistoryCollection()) {
            System.out.println(command);
        }
    }
}
