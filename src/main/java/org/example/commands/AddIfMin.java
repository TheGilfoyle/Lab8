package org.example.commands;

import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;

public class AddIfMin extends Command {
    public AddIfMin() {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
    }

//    @Override
//    public void execute() {
//        DataCollector collector = new DataCollector();
//        MusicBand musicBand = collector.wrap();
//        if (m) {}
//    }
}
