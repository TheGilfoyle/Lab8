package org.example.commands;

import org.example.Main;
import org.example.managers.CollectionManager;
import org.example.managers.ComparatorOfNumberOfParticipants;
import org.example.usualClasses.MusicBand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RemoveLower extends Command {
    public RemoveLower() {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный",1);
    }

    @Override
    public void execute() {
        super.execute();
        int amountOfParticipants = Integer.parseInt(Main.console.getToken(1));
        int isItTrue = 0;
        for(MusicBand musicBand: cm.getMusicBands()){
            if(musicBand.getNumberOfParticipants() < amountOfParticipants){
                cm.removeByID(musicBand.getId());
                isItTrue++;
            }
        }
        if(isItTrue == 0){
            System.out.println("Нет музыкальных групп у которых количество участников меньше чем введёное число участников");
        }
    }
}
