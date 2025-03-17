package org.example.commands;

public class Exit extends Command {
    /**
     * Instantiates a new Exit.
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)", 0);
    }


    /**
     * Command to leave from app.
     */
    @Override
    public void execute() {
        System.exit(0);
    }
}
