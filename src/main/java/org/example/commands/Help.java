package org.example.commands;

import java.io.PrintStream;

public class Help extends Command {
    public Help() {
        super("Help", "Вывести справку по доступным командам");
    }
    @Override
    public void execute(String args) {
    }

}
