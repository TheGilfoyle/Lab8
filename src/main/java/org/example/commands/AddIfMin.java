package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;
import org.example.model.Studio;


/**
 * Команда для добавления нового элемента в коллекцию,
 * если его значение меньше, чем у наименьшего элемента коллекции.
 */
public class AddIfMin extends Command {

    /**
     * Создаёт команду "add_if_min" с описанием и количеством аргументов.
     */
    public AddIfMin() {
        super("add_if_min", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0);
    }

    /**
     * Возвращает количество аргументов в зависимости от режима работы программы.
     * @return количество аргументов
     */
    @Override
    public int getArgsAmount() {
        return Main.scriptMode ? 6 : 0;
    }
    /**
     * Выполнение команды
     */
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
    /**
     * Выполнение команды в режиме скрипта.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 6) {
            return;
        }

        try {
            String name = args[0].trim();
            if (name.isEmpty()) {
                return;
            }

            int x = Integer.parseInt(args[1]);
            long y = Long.parseLong(args[2]);
            long numberOfParticipants = Long.parseLong(args[3]);
            if (numberOfParticipants <= 0) {
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

            MusicBand currentMin = cm.getMinMusicBand();

            if (currentMin == null || musicBand.compareTo(currentMin) < 0) {
                cm.addBand(musicBand);
                System.out.println("Элемент добавлен в коллекцию...");
            } else {
                System.out.println("Этот элемент не является минимальным, поэтому он не был добавлен.");
            }

            super.execute();

        } catch (NumberFormatException ignored) {
        }
    }

}
