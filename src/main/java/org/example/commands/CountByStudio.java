package org.example.commands;


import org.example.Main;
import org.example.model.MusicBand;

/**
 * Класс команды "count_by_studio"
 * Количество групп с заданной студией
 */
public class CountByStudio extends Command {
    /**
     * Конструктор класса
     * Устанавливает имя команды и ее описание
     */
    public CountByStudio() {
        super("count_by_studio", "вывести количество элементов, значение поля studio которых равно заданному", 1);
    }

    /**
     * Метод для выполнения команды
     * Выводит количество групп с заданной студией
     */
    @Override
    public void execute() {
        super.execute();
        long counter = 0;
        String studio = Main.console.getToken(1);
        for (MusicBand musicBand : cm.getMusicBands()) {
            if (musicBand.getStudio() != null && studio != null && musicBand.getStudio().getName().equals(studio)) {
                counter++;
            }
        }
        System.out.println("Вот аж столько музыкальных групп со студией \"" + studio + "\": " + counter);
    }
}
