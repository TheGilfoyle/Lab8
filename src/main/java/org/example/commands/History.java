package org.example.commands;

public class History extends Command {
    public History() {
        super("history", "вывести последние 11 команд (без их аргументов)");
    }

    @Override
    public void execute(String args) {

    }
}
