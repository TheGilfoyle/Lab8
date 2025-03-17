package org.example.commands;

/**
 * Класс команды "info"
 *
 */
public class Info extends Command {
    /**
     * Конструктор класса
     *
     */
    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
    }

    /**
     * Метод для выполнения команды
     *
     */
    @Override
    public void execute() {
        super.execute();
        cm.info();
    }
}
