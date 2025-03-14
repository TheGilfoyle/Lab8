package org.example.usualClasses;

import java.util.HashSet;

/**
 * Класс уникальной генерации ID
 */
public class IDgen {
    private static int idCounter = 1;
    private static HashSet<Integer> usedIDs = new HashSet<>();

    /** Генерирует уникальный ID
     * @return
     */
    public static int generateID() {
        int newID = 1;
        while (usedIDs.contains(newID)) {
            newID++;
        }
        usedIDs.add(newID);
        return newID;
    }

    /** Регистрирует ID (если уже существует — выбрасывает ошибку)
     * @param id
     */
    public static void registerID(int id) {
        if (usedIDs.contains(id)) {
            throw new IllegalArgumentException("Ошибка: ID " + id + " уже используется!");
        }
        usedIDs.add(id);
        idCounter = Math.max(idCounter, id + 1);
    }

    /** Освобождает ID
     * @param id
     */
    public static void releaseID(int id) {
        usedIDs.remove(id);
    }
}