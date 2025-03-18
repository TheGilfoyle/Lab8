package org.example.commands;


import static org.example.Main.inv;

/**
 * Класс команды help
 */
public class Help extends Command {
    /**
     * Конструктор команды
     */
    public Help() {
        super("Help", "Вывести справку по доступным командам", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        super.execute();
        for (Command command : inv.commands.values()) {
            System.out.println(command.nameOfCommand + ": " + command.getDescription());
        }
    }

}
