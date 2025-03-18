package org.example.managers;

import org.example.model.IdGen;
import org.example.model.MusicBand;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Класс управления коллекцией
 *
 * @see MusicBand
 */
public class CollectionManager {
    /**
     * Хэшсет коллекции, в котором хранятся элементы
     *
     */
    HashSet<MusicBand> bands = new HashSet<>();
    /**
     * Дата создания коллекции
     *
     */
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Конструктор по умолчанию
     */
    public CollectionManager() {
    }

    /**
     * Добавляет элемент в коллекцию
     *
     * @param band элемент для добавления
     */
    public void addBand(MusicBand band) {
        bands.add(band);
    }

    /**
     * Выводит информацию о коллекции
     *
     * @param id элемента
     * @return Возвращает музыкальную группу с заданным ID
     */
    public MusicBand getMusicBandByID(long id) {
        for (MusicBand musicBand : bands) {
            if (musicBand.getId() == id) {
                return musicBand;
            }
        }
        System.out.println("Элемента с таким id не обнаружено");
        return null;
    }

    /**
     * Получает минимальную музыкальную группу из коллекции
     *
     * @return Возвращает музыкальную группу с минимальным количеством участников
     */
    public MusicBand getMinMusicBand() {
        if (bands.isEmpty()) {
            return null;
        }
        return Collections.min(bands);
    }


    /**
     * Удаляет все элементы коллекции
     *
     * @param id ID элемента, который нужно удалить
     *
     */
    public void removeByID(int id) {
        MusicBand musicBand = getMusicBandByID(id);
        if (musicBand != null) {
            bands.remove(musicBand);
            IdGen.releaseID(musicBand.getId());
            System.out.println("Элемент с ID: " + id + " удалён...");
        }
    }

    /**
     * Выводит информацию о коллекции
     *
     * @param id ID элемента, который нужно обновить
     */
    public void updateID(int id) {
        DataCollector dataCollector = new DataCollector();
        try {
            MusicBand band = getMusicBandByID(id);
            if (band != null) {
                bands.remove(band);
                IdGen.releaseID(id);
                MusicBand musicBand = dataCollector.wrap();
                bands.add(musicBand);
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Введите значение int > 0");
        }

    }

    /**
     * Выводит информацию о коллекции
     *
     * @return Возвращает коллекцию музыкальных групп
     */
    public HashSet<MusicBand> getMusicBands() {
        return bands;
    }

    /**
     * Выводит информацию о коллекции
     *
     * @param newColl новая коллекция
     */
    public void setCollection(HashSet<MusicBand> newColl) {
        this.bands = newColl;
    }

    /**
     * Выводит информацию о коллекции
     *
     * @return Возвращает коллекцию в формате строки
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (MusicBand band : bands) {
            s.append(band.fileToString()).append("\n");
        }
        return s.toString();
    }

    /**
     * Выводит информацию о коллекции
     */
    public void info() {
        System.out.println("Тип коллекции: HashSet, Дата создания: " + creationDate.toString() + ", Количество элементов: " + bands.size());
    }
}
