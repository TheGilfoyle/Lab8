package org.example.commands;

import org.example.Main;
import org.example.interfaces.Commander;
import org.example.managers.CollectionManager;
import org.example.managers.DBManager;

/**
 * Класс, отвечающий за работу с командами.
 */
public abstract class Command implements Commander {
    /**
     * Ссылка на класс CollectionManager.
     * Предоставляет доступ к методам класса.
     */
    protected static CollectionManager cm = Main.cm;
    protected static DBManager db = Main.db;
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
     * @param name        команды.
     * @param description описание команды.
     * @param argsAmount  количество аргументов команды.
     */
    public Command(String name, String description, int argsAmount) {
        this.nameOfCommand = name;
        this.description = description;
        this.argsAmount = argsAmount;
    }

    /**
     * Возвращает имя команды.
     *
     * @return Возвращает имя команды.
     */
    public String getNameOfCommand() {
        return nameOfCommand;
    }

    /**
     * Возвращает описание команды.
     *
     * @return Возвращает описание команды.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает количество аргументов команды.
     *
     * @return Возвращает количество аргументов команды.
     */
    public int getArgsAmount() {
        return argsAmount;
    }

    /**
     * Делает проверку метода на корректное выполнение
     *
     * @return возвращает результат проверки
     */
    public boolean check(String[] args) {
        return true;
    }

    /**
     * Метод, отвечающий за выполнение команды.
     */
    @Override
    public void execute() {
        Main.hc.addCommand(getNameOfCommand());
    }

    @Override
    public void execute(String[] args) {
    }

    /**
     * Выводит описание команды.
     */
    @Override
    public void description() {
        System.out.println(nameOfCommand + " - " + description);
    }
}
