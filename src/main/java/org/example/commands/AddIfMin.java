package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;

public class AddIfMin extends Command {
    public AddIfMin() {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0);
    }

    @Override
    public void execute() {
        super.execute();
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        if (Main.cm.getMinMusicBand() == null || Main.cm.getMinMusicBand().compareTo(musicBand) > 0) {
            cm.addBand(musicBand);
            System.out.println("Элемент добавлен в коллекцию...");
        } else {
            System.out.println("Этот элемент не имеет минимальное количество участников, поэтому он не был добавлен... :( ");
        }
    }
}
