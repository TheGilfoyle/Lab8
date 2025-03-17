package org.example.commands;

public class Info extends Command {
    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
    }

    @Override
    public void execute() {
        super.execute();
        cm.info();
    }
}
