package org.example.commands;


import static org.example.Main.inv;

public class Help extends Command {
    public Help() {
        super("Help", "Вывести справку по доступным командам", 0);
    }

    @Override
    public void execute() {
        super.execute();
        for (Command command : inv.commands.values()) {
            System.out.println(command.nameOfCommand + ": " + command.getDescription());
        }
    }

}
