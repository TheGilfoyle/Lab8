package org.example.commands;

public class Exit extends Command {
    /**
     * Instantiates a new Exit.
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute(String args) {}

    /**
     * Command to leave from app.
     */
    @Override
    public void execute() {
        System.exit(0);
    }
}
