package org.example.commands;


import org.example.Main;
import org.example.usualClasses.MusicBand;

public class CountByStudio extends Command {
    public CountByStudio() {
        super("count_by_studio", "вывести количество элементов, значение поля studio которых равно заданному", 1);
    }

    @Override
    public void execute() {
        super.execute();
        long counter = 0;
        String studio = Main.console.getToken(1);
        for (MusicBand musicBand : cm.getMusicBands()) {
            if (musicBand.getStudio() != null && studio != null && musicBand.getStudio().getName().equals(studio)) {
                counter++;
            }
        }
        System.out.println("Вот аж столько музыкальных групп со студией \"" + studio + "\": " + counter);
    }
}
