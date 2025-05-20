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
     * Выполнение команды
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

    /**
     * Проверяет, что переданные аргументы соответствуют ожиданиям.
     *
     * @param args
     * @return true, если аргументы соответствуют ожиданиям, иначе false
     */
    @Override
    public boolean check(String[] args) {
        if (args.length != 1) return false;

        String studio = args[0];

        for (MusicBand musicBand : cm.getMusicBands()) {
            if (musicBand.getStudio() != null && musicBand.getStudio().getName().equals(studio)) {
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
        if (args.length != 1) return;

        String studio = args[0];
        long counter = 0;

        for (MusicBand musicBand : cm.getMusicBands()) {
            if (musicBand.getStudio() != null && studio != null && musicBand.getStudio().getName().equals(studio)) {
                counter++;
            }
        }

        System.out.println("Вот аж столько музыкальных групп со студией \"" + studio + "\": " + counter);
        super.execute();
    }
}
