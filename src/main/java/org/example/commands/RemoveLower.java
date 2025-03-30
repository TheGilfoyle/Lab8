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
                MusicBand referenceBand = new MusicBand();
                referenceBand.setNumberOfParticipants(amountOfParticipants);

                Set<MusicBand> bandsToRemove = new HashSet<>();
                int amountToRemove = 0;
                for (MusicBand musicBand : cm.getMusicBands()) {
                    if (comparator.compare(musicBand, referenceBand) < 0) {
                        bandsToRemove.add(musicBand);
                        amountToRemove++;
                    }
                }

                if (amountToRemove == 0 || cm.getMusicBands().isEmpty()) {
                    if (amountToRemove == 0 && !cm.getMusicBands().isEmpty()) {
                        System.out.println("Нет музыкальных групп, у которых количество участников меньше чем " + amountOfParticipants);
                    } else System.out.println("Коллекция в принципе не содержит элементов...");
                } else {
                    cm.getMusicBands().removeAll(bandsToRemove);
                    System.out.println("Удалено " + bandsToRemove.size() + " музыкальных групп с количеством участников меньше чем " + amountOfParticipants);
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
            MusicBand referenceBand = new MusicBand();
            referenceBand.setNumberOfParticipants(amountOfParticipants);

            Set<MusicBand> bandsToRemove = new HashSet<>();
            int amountToRemove = 0;
            for (MusicBand musicBand : cm.getMusicBands()) {
                if (comparator.compare(musicBand, referenceBand) < 0) {
                    bandsToRemove.add(musicBand);
                    amountToRemove++;
                }
            }

            if (cm.getMusicBands().isEmpty()) {
                System.out.println("Коллекция в принципе не содержит элементов...");
            } else if (amountToRemove == 0) {
                System.out.println("Нет музыкальных групп, у которых количество участников меньше чем " + amountOfParticipants);
            } else {
                cm.getMusicBands().removeAll(bandsToRemove);
                System.out.println("Удалено " + bandsToRemove.size() + " музыкальных групп.");
            }

            super.execute();

        } catch (NumberFormatException ignored) {

        }
    }

}

