package org.example.commands;

import org.example.managers.DataCollector;
import org.example.model.MusicBand;

/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class Add extends Command {

    /**
     * Создаёт команду "add" с описанием и количеством аргументов.
     */
    public Add() {
        super("add", "Добавить новый элемент в коллекцию", 0);
    }

    /**
     * Выполняет команду добавления нового элемента.
     * Запрашивает данные у пользователя, создаёт объект {@link MusicBand}
     * и добавляет его в коллекцию.
     */
    @Override
    public void execute() {
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();
        cm.addBand(musicBand);
        super.execute();
    }
}
