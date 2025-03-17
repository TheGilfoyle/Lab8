package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;

/**
 * Класс команды "remove_by_id"
 *
 */
public class RemoveByID extends Command {
    /**
     * Конструктор команды "remove_by_id"
     *
     */
    public RemoveByID() {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1);
    }

    /**
     * Метод, исполняющий команду
     *
     */
    @Override
    public void execute() {
        try {
            String removingID = Main.console.getToken(1);

            if (!removingID.matches("^\\d+$")) {
                throw new InvalidDataException("В качестве аргументов могут быть только числа long больше ноля, будьте добры, соблюдайте правила");
            }
            int id = Integer.parseInt(removingID);
            cm.removeByID(id);
            super.execute();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

}
