package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;
import org.example.managers.ComparatorOfNumberOfParticipants;
import org.example.model.MusicBand;

import java.util.*;

/**
 * Класс команды remove_lower
 */
public class RemoveLower extends Command {
    private String cachedLine = null;
    /**
     * Конструктор
     */
    private final Comparator<MusicBand> comparator = new ComparatorOfNumberOfParticipants();

    /**
     * Конструктор команды remove_lower
     */
    public RemoveLower() {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный", 1);
    }

    /**
     * Проверяет, что переданные аргументы соответствуют ожиданиям.
     * @param args
     * @return true, если аргументы соответствуют ожиданиям, иначе false
     */
    @Override
    public boolean check(String[] args) {
        if (args.length != 1) return false;

        String amountOfParticipantsString = args[0];
        if (!amountOfParticipantsString.matches("^\\d+$")) return false;

        long amountOfParticipants = Long.parseLong(amountOfParticipantsString);
        MusicBand referenceBand = new MusicBand();
        referenceBand.setNumberOfParticipants(amountOfParticipants);

        for (MusicBand musicBand : cm.getMusicBands()) {
            if (comparator.compare(musicBand, referenceBand) < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        if (!Main.scriptMode) {
            try {
                String amountOfParticipantsString = Main.console.getToken(1);

                if (!amountOfParticipantsString.matches("^\\d+$")) {
                    throw new InvalidDataException("В качестве аргументов могут быть только числа, будьте добры, соблюдайте правила");
                }
                long amountOfParticipants = Long.parseLong(amountOfParticipantsString);

                Set<MusicBand> bandsToRemove = new HashSet<>();
                for (MusicBand mb : cm.getMusicBands()) {
                    if (comparator.compare(mb, new MusicBand() {{ setNumberOfParticipants(amountOfParticipants); }}) < 0) {
                        bandsToRemove.add(mb);
                    }
                }

                if (bandsToRemove.isEmpty()) {
                    if (cm.getMusicBands().isEmpty()) {
                        System.out.println("Коллекция в принципе не содержит элементов...");
                    } else {
                        System.out.println("Нет музыкальных групп, у которых количество участников меньше чем " + amountOfParticipants);
                    }
                } else {
                    int removedCount = 0;
                    for (MusicBand mb : bandsToRemove) {
                        if (Main.db.removeByID(Main.currentUser, mb.getId())) {
                            cm.removeByID(mb.getId());
                            removedCount++;
                        }
                    }
                    System.out.println("Удалено " + removedCount +
                            " музыкальных групп с количеством участников меньше чем " + amountOfParticipantsString);
                }

                super.execute();
            } catch (InvalidDataException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Слишком много цифр... Не потянет");
            }
        }
    }

    /**
     * Выполнение команды в режиме скрипта.
     */
    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 1) {
                return;
            }

            String amountOfParticipantsString = args[0];

            if (!amountOfParticipantsString.matches("^\\d+$")) {
                return;
            }

            long amountOfParticipants = Long.parseLong(amountOfParticipantsString);
            Set<MusicBand> bandsToRemove = new HashSet<>();
            for (MusicBand mb : cm.getMusicBands()) {
                if (comparator.compare(mb, new MusicBand() {{ setNumberOfParticipants(amountOfParticipants); }}) < 0) {
                    bandsToRemove.add(mb);
                }
            }
            if (cm.getMusicBands().isEmpty()) {
                System.out.println("Коллекция в принципе не содержит элементов...");
            } else if (bandsToRemove.isEmpty()) {
                System.out.println("Нет музыкальных групп, у которых количество участников меньше чем " + amountOfParticipants);
            } else {
                int removedCount = 0;
                for (MusicBand mb : bandsToRemove) {
                    if (Main.db.removeByID(Main.currentUser, mb.getId())) {
                        cm.removeByID(mb.getId());
                        removedCount++;
                    }
                }
                System.out.println("Удалено " + removedCount + " музыкальных групп.");
            }
            super.execute();

        } catch (NumberFormatException ignored) {

        }
    }

}

