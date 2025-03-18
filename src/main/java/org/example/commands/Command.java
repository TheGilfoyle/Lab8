package org.example.commands;

import org.example.Main;
import org.example.interfaces.Commander;
import org.example.managers.CollectionManager;

/**
 * Класс, отвечающий за работу с командами.
 */
public abstract class Command implements Commander {
    /**
     * Ссылка на класс CollectionManager.
     * Предоставляет доступ к методам класса.
     */
    protected static CollectionManager cm = Main.cm;
    /**
     * Имя команды.
     */
    protected String nameOfCommand;
    /**
     * Описание команды.
     */
    protected String description;
    /**
     * Количество аргументов команды.
     */
    protected int argsAmount;

    /**
     * Конструктор класса.
     *
     * @param name
     * @param description
     * @param argsAmount
     */
    public Command(String name, String description, int argsAmount) {
        this.nameOfCommand = name;
        this.description = description;
        this.argsAmount = argsAmount;
    }

    /**
     * Возвращает имя команды.
     *
     * @return
     */
    public String getNameOfCommand() {
        return nameOfCommand;
    }

    /**
     * Возвращает описание команды.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает количество аргументов команды.
     *
     */
    public int getArgsAmount() {
        return argsAmount;
    }

    /**
     * Метод, отвечающий за выполнение команды.
     *
     */
    @Override
    public void execute() {
        Main.hc.addCommand(getNameOfCommand());
    }

    /**
     * Выводит описание команды.
     */
    @Override
    public void description() {
        System.out.println(nameOfCommand + " - " + description);
    }
}
