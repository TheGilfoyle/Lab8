package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;

public class CountGreaterThanGenre extends Command {
    public CountGreaterThanGenre() {
        super("count_greater_than_genre", "вывести количество элементов, значение поля genre которых больше заданного", 1);
    }

    @Override
    public void execute() {
        long counter = 0;
        try {
            String genreString = Main.console.getToken(1).toUpperCase();
            boolean isValidGenre = false;
            for (MusicGenre genre : MusicGenre.values()) {
                if (genre.name().equals(genreString)) {
                    isValidGenre = true;
                    break;
                }
            }
            if (!isValidGenre) {
                throw new InvalidDataException("Жанр '" + Main.console.getToken(1) + "' не найден. Попробуйте ещё раз.");
            }
            MusicGenre genre = MusicGenre.valueOf(genreString);
            for (MusicBand musicBand : cm.getMusicBands()) {
                if (musicBand.getGenre() != null && musicBand.getGenre().getValue() > genre.getValue()) {
                    counter++;
                }
            }
            System.out.println("Вот аж столько музыкальных групп с жанром больше чем " + genreString + ": " + counter);
            super.execute();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }

    }
}