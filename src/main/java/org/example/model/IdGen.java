package org.example.model;

import java.util.HashSet;

/**
 * Класс для уникальной генерации ID.
 * Позволяет создавать, регистрировать и освобождать идентификаторы.
 */
public class IdGen {
    /**
     * Конструктор по умолчанию.
     */
    public IdGen() {
    }

    /**
     * Счётчик для генерации уникальных ID.
     */
    private static int idCounter = 1;
    /**
     * Множество использованных ID.
     */
    private static HashSet<Integer> usedIDs = new HashSet<>();

    /**
     * Генерирует уникальный ID, который ещё не использовался.
     *
     * @return уникальный идентификатор
     */
    public static int generateID() {
        int newID = 1;
        while (usedIDs.contains(newID)) {
            newID++;
        }
        usedIDs.add(newID);
        return newID;
    }

    /**
     * Регистрирует переданный ID, если он ещё не используется.
     * Если ID уже существует, выбрасывает исключение.
     *
     * @param id идентификатор для регистрации
     * @throws IllegalArgumentException если ID уже используется
     */
    public static void registerID(int id) {
        if (usedIDs.contains(id)) {
            throw new IllegalArgumentException("Ошибка: ID " + id + " уже используется!");
        }
        usedIDs.add(id);
        idCounter = Math.max(idCounter, id + 1);
    }

    /**
     * Освобождает ID, делая его доступным для повторного использования.
     *
     * @param id идентификатор для освобождения
     */
    public static void releaseID(int id) {
        usedIDs.remove(id);
    }
}
