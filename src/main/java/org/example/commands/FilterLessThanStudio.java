package org.example.commands;

import org.example.Main;
import org.example.usualClasses.MusicBand;

public class FilterLessThanStudio extends Command {
    public FilterLessThanStudio() {
        super("filter_less_than_studio", "вывести элементы, значение поля studio которых меньше заданного",1);
    }

    @Override
    public void execute() {
        super.execute();
        String nameStudio = Main.console.getToken(1);
        int nameStudioLength = nameStudio.length();
        for (MusicBand musicBand: cm.getMusicBands()) {
            if (musicBand.getStudio().getName().length() < nameStudioLength) {
                System.out.println(musicBand);
            }
        }
        System.out.println("Вот все элементы значений студии которых меньше чем то что ты написал");
    }
}
