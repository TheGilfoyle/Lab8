package org.example.commands;

import org.example.Main;
import org.example.exceptions.InvalidDataException;


/**
 * Класс команды "remove_by_id"
 */
public class RemoveByID extends Command {
    /**
     * Конструктор команды "remove_by_id"
     */
    public RemoveByID() {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1);
    }
    /**
     * Проверяет, что переданные аргументы соответствуют ожиданиям.
     * @param args аргументы
     * @return true, если аргументы соответствуют ожиданиям, иначе false
     */
    @Override
    public boolean check(String[] args) {
        if (args.length != 1) return false;
        if (!args[0].matches("^\\d+$")) return false;

        int id = Integer.parseInt(args[0]);
        return cm.getMusicBandByID(id) != null;
    }
    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        try {
            String removingID = Main.console.getToken(1);

            if (!removingID.matches("^\\d+$")) {
                throw new InvalidDataException("В качестве аргументов могут быть только числа long больше ноля, будьте добры, соблюдайте правила");
            }
            int id = Integer.parseInt(removingID);
            cm.removeByID(id);
            if (cm.getMusicBandByID(id) != null) {
                super.execute();
            }
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Выполнение команды в режиме скрипта.
     */
    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 1) {
                return;
            }

            String removingID = args[0];

            if (!removingID.matches("^\\d+$")) {
                return;
            }

            int id = Integer.parseInt(removingID);
            cm.removeByID(id);

            if (cm.getMusicBandByID(id) != null) {
                super.execute();
            }

        } catch (NumberFormatException ignored) {
        }
    }
}
