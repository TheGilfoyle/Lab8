package org.example.commands;

import org.example.usualClasses.MusicBand;

import java.util.HashSet;

/**
 * Класс команды "clear".
 * Очищает коллекцию.
 *
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
     * Метод для выполнения команды.
     * Проверяет, не пуста ли коллекция, и если нет, то очищает её.
     * Если коллекция пуста, то выводит сообщение об этом.
     *
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
