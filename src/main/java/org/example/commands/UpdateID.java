package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;


public class UpdateID extends Command {
    public UpdateID() {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному", 1);
    }

    @Override
    public void execute() {

        try {
            String updatingID = Main.console.getToken(1);
            if (!updatingID.matches("^\\d+$")) {
                throw new InvalidDataException("В качестве аргументов могут быть только числа long больше ноля, будьте добры, соблюдайте правила");
            }
            int id = Integer.parseInt(updatingID);
            cm.updateID(id);
            super.execute();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
