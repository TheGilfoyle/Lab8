package org.example.commands;

import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;


public class Add extends Command {
    public Add() {
        super("add", "Добавить новый элемент в коллекцию");
    }

    @Override
    public void execute() {
        super.execute();
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        cm.addBand(musicBand);
    }
}
