package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;
import org.example.model.Studio;

/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class Add extends Command {

    /**
     * Создаёт команду "add" с описанием и количеством аргументов.
     */
    public Add() {
        super("add", "Добавить новый элемент в коллекцию", 0);
    }

    /**
     * Возвращает количество аргументов в зависимости от режима работы программы.
     *
     * @return Количество аргументов
     */
    @Override
    public int getArgsAmount() {
        return Main.scriptMode ? 6 : 0;
    }

    /**
     * Проверяет, что переданные аргументы соответствуют ожиданиям.
     * @param args
     * @return true, если аргументы соответствуют ожиданиям, иначе false
     */
    @Override
    public boolean check(String[] args) {
        if (args.length != 6) return false;

        String name = args[0];
        if (name == null || name.trim().isEmpty()) return false;

        try {
            Integer.parseInt(args[1]);
            Long.parseLong(args[2]);
            long participants = Long.parseLong(args[3]);
            if (participants <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        if (!args[4].equalsIgnoreCase("null")) {
            try {
                MusicGenre.valueOf(args[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }
    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        super.execute();
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        if (musicBand != null) {
            cm.addBand(musicBand);
            Main.db.addMusicBand(musicBand, Main.currentUser);

        }
    }
    /**
     * Выполнение команды в режиме скрипта
     */
    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 6) {
                return;
            }

            String name = args[0].trim();
            if (name.isEmpty()) {
                return;
            }

            int x;
            long y;
            long numberOfParticipants;

            try {
                x = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return;
            }

            try {
                y = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                return;
            }

            try {
                numberOfParticipants = Long.parseLong(args[3]);
                if (numberOfParticipants <= 0) {
                    return;
                }
            } catch (NumberFormatException e) {
                return;
            }

            MusicGenre genre = null;
            if (!args[4].equalsIgnoreCase("null")) {
                try {
                    genre = MusicGenre.valueOf(args[4].toUpperCase());
                } catch (IllegalArgumentException e) {
                    return;
                }
            }

            Studio studio = null;
            if (!args[5].equalsIgnoreCase("null")) {
                studio = new Studio();
                studio.setName(args[5]);
            }

            Coordinates coordinates = new Coordinates();
            coordinates.setX(x);
            coordinates.setY(y);

            MusicBand musicBand = new MusicBand();
            musicBand.setName(name);
            musicBand.setCoordinates(coordinates);
            musicBand.setNumberOfParticipants(numberOfParticipants);
            musicBand.setGenre(genre);
            musicBand.setStudio(studio);

            cm.addBand(musicBand);
            super.execute();

        } catch (Exception ignored) {
        }
    }

}
