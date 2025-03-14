package org.example.commands;


import org.example.Main;
import org.example.usualClasses.MusicBand;

public class CountByStudio extends Command {
    public CountByStudio() {
        super("count_by_studio","вывести количество элементов, значение поля studio которых равно заданному");
    }

    @Override
    public void execute() {
        super.execute();
        long counter=0;
        String studio = Main.console.getToken(1);
        for (MusicBand musicBand: cm.getMusicBands()){
            if (musicBand.getStudio().equals(studio)){
                counter++;
            }
        }
        System.out.println("Вот аж столько музыкальных групп со студии "+ counter);
    }
}
