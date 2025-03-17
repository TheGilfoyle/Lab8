package org.example.commands;

import org.example.Main;
import org.example.usualClasses.MusicBand;

public class FilterLessThanStudio extends Command {
    public FilterLessThanStudio() {
        super("filter_less_than_studio", "вывести элементы, значение поля studio которых меньше заданного", 1);
    }

    @Override
    public void execute() {
        super.execute();
        String nameStudio = Main.console.getToken(1);
        int nameStudioLength = nameStudio.length();
        int count = 0;
        for (MusicBand musicBand : cm.getMusicBands()) {
            if (musicBand.getStudio() != null && musicBand.getStudio().getName() != null & musicBand.getStudio().getName().length() < nameStudioLength) {
                System.out.println(musicBand);
                count++;
            }
        }
        if (count == 0 || cm.getMusicBands().isEmpty()) {
            if (count == 0 && !cm.getMusicBands().isEmpty()) {
                System.out.println("В коллекции нет элементов, длина имени студии которых меньше, чем длина имени студии \"" + nameStudio + "\"");
            } else System.out.println("Коллекция в принципе не содержит элементов...");
        } else {
            System.out.println("Вот все элементы, длина имени студии которых меньше чем \"" + nameStudio + "\"");
        }
    }
}
