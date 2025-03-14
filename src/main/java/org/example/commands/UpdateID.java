package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;

public class UpdateID extends Command {
    public UpdateID() {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public void execute() {
        super.execute();
        int id = Integer.parseInt(Main.console.getToken(1));
        int idOfPreviousElement =0;
        for (MusicBand musicBand: cm.getMusicBands()){
            if (musicBand.getId() == id){
                idOfPreviousElement = musicBand.getId();
                break;
            }
        }
        cm.removeByID(1);
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        musicBand.setId(idOfPreviousElement);
        cm.addBand(musicBand);
    }
}
