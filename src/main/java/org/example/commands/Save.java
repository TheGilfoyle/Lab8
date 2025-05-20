package org.example.commands;

import org.example.Main;
import org.example.managers.FileWriter;

/**
 * Класс для команды save
 */
public class Save extends Command {
    /**
     * Конструктор команды
     */
    public Save() {
        super("save", "сохранить коллекцию в файл", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
//        FileWriter.writeToFile(Main.cm.getMusicBands());
//        super.execute();
//        System.out.println("Элементы успешно сохранены в файл");
    }
}
