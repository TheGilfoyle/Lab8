package org.example.commands;

import org.example.usualClasses.MusicBand;

import java.util.HashSet;

public class Clear extends Command {
    public Clear() {
        super("clear", " очистить коллекцию",0);
    }

    @Override
    public void execute() {
        super.execute();
        cm.setCollection(new HashSet<MusicBand>());
        System.out.println("Коллекция очищена...");
    }

}
