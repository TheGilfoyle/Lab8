package org.example.commands;

import org.example.Main;


public class History extends Command {

    public History() {
        super("history", "вывести последние 11 команд (без их аргументов)", 0);
    }

    @Override
    public void execute() {
        super.execute();
        for (String command : Main.hc.getHistoryCollection()) {
            System.out.println(command);
        }
    }
}
