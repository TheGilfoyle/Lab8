package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;
import org.example.managers.DataCollector;
import org.example.model.*;

/**
 * Класс команды, обновляющий элемент коллекции по его id
 */
public class UpdateID extends Command {
    /**
     * Конструктор класса
     */
    public UpdateID() {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному", 1);
    }

    /**
     * Возвращает количество аргументов в зависимости от режима работы программы.
     *
     * @return количество аргументов
     */
    @Override
    public int getArgsAmount() {
        return Main.scriptMode ? 7 : 1;
    }

    /**
     * Проверяет, что переданные аргументы соответствуют ожиданиям.
     *
     * @param args аргументы
     * @return true, если аргументы соответствуют ожиданиям, иначе false
     */
    @Override
    public boolean check(String[] args) {
        if (args.length != 7) return false;
        if (!args[0].matches("^\\d+$")) return false;

        int id = Integer.parseInt(args[0]);
        return cm.getMusicBandByID(id) != null;
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        try {
            String updatingID = Main.console.getToken(1);
            if (!updatingID.matches("^\\d+$")) {
                throw new InvalidDataException("В качестве аргументов могут быть только числа long больше ноля, будьте добры, соблюдайте правила");
            }
            int id = Integer.parseInt(updatingID);

            DataCollector dc = new DataCollector();
            MusicBand updated = dc.wrap();

            updated.setId(id);

            boolean ok = Main.db.updateMusicBand(id, updated, Main.currentUser);
            if (!ok) {
                System.out.println("Не удалось обновить группу в БД.");
                return;
            }

            Main.cm.removeByID(id);
            Main.cm.addBand(updated);
            System.out.println("Группа с id " + id + " успешно обновлена.");
            super.execute();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выполнение команды в режиме скрипта.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 7) {
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            MusicBand existing = cm.getMusicBandByID(id);
            if (existing == null) {
                return;
            }

            String name = args[1].trim();
            if (name.isEmpty()) {
                return;
            }

            int x = Integer.parseInt(args[2]);
            long y = Long.parseLong(args[3]);

            long participants = Long.parseLong(args[4]);
            if (participants <= 0) {
                return;
            }

            MusicGenre genre = null;
            if (!args[5].equalsIgnoreCase("null")) {
                try {
                    genre = MusicGenre.valueOf(args[5].toUpperCase());
                } catch (IllegalArgumentException e) {
                    return;
                }
            }

            Studio studio = null;
            if (!args[6].equalsIgnoreCase("null")) {
                studio = new Studio();
                studio.setName(args[6]);
            }

            Coordinates coords = new Coordinates();
            coords.setX(x);
            coords.setY(y);

            MusicBand updated = new MusicBand();
            updated.setName(name);
            updated.setCoordinates(coords);
            updated.setNumberOfParticipants(participants);
            updated.setGenre(genre);
            updated.setStudio(studio);

            boolean ok = Main.db.updateMusicBand(id, updated, Main.currentUser);
            if (!ok) {
                return;
            }

            Main.cm.removeByID(id);
            Main.cm.addBand(updated);

            System.out.println("Группа с id " + id + " успешно обновлена.");
            super.execute();

        } catch (NumberFormatException ignored) {
        }
    }

}
