package org.example.commands;

import org.example.Main;

public class RemoveByID extends Command {
    public RemoveByID() {
        super("remove_by_id", "удалить элемент из коллекции по его id",1);
    }

    @Override
    public void execute() {
        super.execute();
        int id = Integer.parseInt(Main.console.getToken(1));
        cm.removeByID(id);
    }

    //    @Override
//    public void execute(String args) {
//
//    }
}
