package org.example.commands;

import org.example.Main;
import org.example.interfaces.Commander;
import org.example.managers.CollectionManager;

import java.util.Scanner;

public abstract class Command  implements Commander {

    protected static CollectionManager cm = Main.cm;

    Scanner sc = new Scanner(System.in);

    protected String nameOfCommand;
    protected String description;
    protected int argsAmount;

    public Command(String name, String description, int argsAmount) {
        this.nameOfCommand = name;
        this.description = description;
        this.argsAmount = argsAmount;
    }

    public String getNameOfCommand() {
        return nameOfCommand;
    }

    public String getDescription() {
        return description;
    }

    public int getArgsAmount() {
        return argsAmount;
    }

    @Override
    public void execute() {
        Main.hc.addCommand(getNameOfCommand());
    }

    @Override
    public void description() {
        System.out.println(nameOfCommand + " - " + description);
    }
}
