package org.example.commands;

import org.example.Main;
import org.example.model.MusicBand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс команды filter_less_than_studio
 */
public class FilterLessThanStudio extends Command {
    /**
     * Конструктор класса команды filter_less_than_studio
     */
    public FilterLessThanStudio() {
        super("filter_less_than_studio", "вывести элементы, значение поля studio которых меньше заданного", 1);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        super.execute();
        String nameStudio = Main.console.getToken(1);
        int nameStudioLength = nameStudio.length();

        List<MusicBand> filteredBands = cm.getMusicBands().stream()
            .filter(musicBand -> 
                musicBand.getStudio() != null && 
                musicBand.getStudio().getName() != null && 
                musicBand.getStudio().getName().length() < nameStudioLength)
            .collect(java.util.stream.Collectors.toList());

        // Print each band
        filteredBands.forEach(System.out::println);

        int count = filteredBands.size();

        if (count == 0 || cm.getMusicBands().isEmpty()) {
            if (count == 0 && !cm.getMusicBands().isEmpty()) {
                // No matching bands but collection is not empty
            } else {
                System.out.println("Коллекция в принципе не содержит элементов...");
            }
        } else {
            System.out.println("Вот все элементы, длина имени студии которых меньше чем \"" + nameStudio + "\"");
        }
    }

    /**
     * Выполнение команды в режиме скрипта.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) return;

        String nameStudio = args[0];
        int nameStudioLength = nameStudio.length();

        List<MusicBand> filteredBands = cm.getMusicBands().stream()
            .filter(musicBand -> 
                musicBand.getStudio() != null && 
                musicBand.getStudio().getName() != null && 
                musicBand.getStudio().getName().length() < nameStudioLength)
            .collect(Collectors.toList());

        // Print each band
        filteredBands.forEach(System.out::println);

        int count = filteredBands.size();

        if (count == 0 || cm.getMusicBands().isEmpty()) {
            if (count == 0 && !cm.getMusicBands().isEmpty()) {
                System.out.println("В коллекции нет элементов, длина имени студии которых меньше, чем длина имени студии \"" + nameStudio + "\"");
            } else {
                System.out.println("Коллекция в принципе не содержит элементов...");
            }
        } else {
            System.out.println("Вот все элементы, длина имени студии которых меньше чем \"" + nameStudio + "\"");
        }

        super.execute();
    }

}
