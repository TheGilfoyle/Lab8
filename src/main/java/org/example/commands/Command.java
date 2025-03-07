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

    public Command(String name, String description) {
        this.nameOfCommand = name;
        this.description = description;
    }

    protected String splite = "----------";

    public String getNameOfCommand() {
        return nameOfCommand;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void execute() {}

    @Override
    public void description() {
        System.out.println(nameOfCommand + " - " + description);
    }
}
