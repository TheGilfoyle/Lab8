package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;
import org.example.managers.ComparatorOfNumberOfParticipants;
import org.example.usualClasses.MusicBand;

import java.util.*;

public class RemoveLower extends Command {
    private final Comparator<MusicBand> comparator = new ComparatorOfNumberOfParticipants();

    public RemoveLower() {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный", 1);
    }

    @Override
    public void execute() {

        try{
            String amountOfParticipantsString = Main.console.getToken(1);

            if (!amountOfParticipantsString.matches("^\\d+$")){
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
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
}
