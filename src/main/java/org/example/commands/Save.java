package org.example.commands;

import org.example.Main;
import org.example.managers.FileWriter;

public class Save extends Command {
    public Save() {
        super("save", "сохранить коллекцию в файл",0);
    }

    @Override
    public void execute() {
        super.execute();
        FileWriter.writeToFile(Main.cm.getMusicBands());
        System.out.println("Элементы успешно сохранены в файл");
    }
}
