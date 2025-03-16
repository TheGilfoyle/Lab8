package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;

public class UpdateID extends Command {
    public UpdateID() {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному",1);
    }

    @Override
    public void execute() {
        super.execute();
        int id = Integer.parseInt(Main.console.getToken(1));
        cm.updateID(id);
    }
}
