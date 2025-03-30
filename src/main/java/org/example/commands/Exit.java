package org.example.commands;

/**
 * Класс команды Exit
 */
public class Exit extends Command {
    /**
     * Конструктор класса
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        System.exit(0);
    }
}
