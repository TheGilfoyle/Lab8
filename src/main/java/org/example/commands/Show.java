package org.example.commands;

import org.example.Main;
import org.example.model.MusicBand;

import java.util.HashSet;

/**
 * Класс команды "show"
 */
public class Show extends Command {
    /**
     * Конструктор команды
     */
    public Show() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        super.execute();
        long amount = 0;
        HashSet<MusicBand> musicBands = cm.getMusicBands();
        for (MusicBand mb : musicBands) {
            System.out.println(mb);
            amount++;
        }
        System.out.println("Количество элементов коллекции: " + amount);
    }
}
