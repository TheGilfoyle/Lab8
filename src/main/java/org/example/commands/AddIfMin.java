package org.example.commands;

import org.example.Main;
import org.example.managers.DataCollector;
import org.example.model.MusicBand;

/**
 * Команда для добавления нового элемента в коллекцию,
 * если его значение меньше, чем у наименьшего элемента коллекции.
 */
public class AddIfMin extends Command {

    /**
     * Создаёт команду "add_if_min" с описанием и количеством аргументов.
     */
    public AddIfMin() {
        super("add_if_min", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0);
    }

    /**
     * Выполняет команду добавления элемента, если он является минимальным.
     * <p>
     * Запрашивает у пользователя данные для создания объекта {@link MusicBand}.
     * Если новый элемент меньше текущего минимального элемента коллекции,
     * он добавляется в коллекцию, иначе выводится сообщение об отказе.
     */
    @Override
    public void execute() {
        super.execute();
        DataCollector collector = new DataCollector();
        MusicBand musicBand = collector.wrap();

        if (Main.cm.getMinMusicBand() == null || Main.cm.getMinMusicBand().compareTo(musicBand) > 0) {
            cm.addBand(musicBand);
            System.out.println("Элемент добавлен в коллекцию...");
        } else {
            System.out.println("Этот элемент не имеет минимальное количество участников, поэтому он не был добавлен... :( ");
        }
    }
}
