package org.example.commands;

import org.example.model.MusicBand;

import java.util.HashSet;

/**
 * Класс команды "clear".
 * Очищает коллекцию.
 */
public class Clear extends Command {
    /**
     * Конструктор.
     * Устанавливает имя команды, описание и количество аргументов.
     */
    public Clear() {
        super("clear", " очистить коллекцию", 0);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        super.execute();
        if (cm.getMusicBands().isEmpty()) {
            System.out.println("Коллекция итак пустая, очищать нечего...");
        } else {
            cm.setCollection(new HashSet<MusicBand>());
            System.out.println("Коллекция очищена...");
        }
    }

}
