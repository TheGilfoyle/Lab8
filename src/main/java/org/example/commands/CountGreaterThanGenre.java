package org.example.commands;

import org.example.Main;
import org.example.usualClasses.MusicBand;
import org.example.usualClasses.MusicGenre;

public class CountGreaterThanGenre extends Command {
    public CountGreaterThanGenre() {
        super("count_greater_than_genre", "вывести количество элементов, значение поля genre которых больше за");
    }
    @Override
    public void execute() {
        super.execute();
        long counter=0;
        String genreString = Main.console.getToken(1);
        MusicGenre genre = MusicGenre.valueOf(genreString);
        for (MusicBand musicBand: cm.getMusicBands()){
            if (musicBand.getGenre().getValue() > genre.getValue()){
                counter++;
            }
        }
        System.out.println("Вот аж столько музыкальных групп с жанром больше введёного жанра: "+ counter);
    }
}
