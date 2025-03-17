package org.example.commands;

import org.example.managers.DataCollector;
import org.example.usualClasses.MusicBand;

/**
 * Класс команды добавления элемента в коллекцию
 */
public class Add extends Command {
    /**
    * Конструктор класса команды
     */
    public Add() {
        super("add", "Добавить новый элемент в коллекцию", 0);
    }

    /**
     * Метод, выполняющий команду
     */
    @Override
    public void execute() {
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        cm.addBand(musicBand);
        super.execute();
    }
}
