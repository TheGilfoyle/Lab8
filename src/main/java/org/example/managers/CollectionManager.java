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
     * @param <MusicBand> коллекция, в которой хранятся элементы
     */
    HashSet<MusicBand> bands = new HashSet<>();
    /**
     * Дата создания коллекции
     *
     * @see LocalDateTime
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
     * @param musicBand элемент для добавления
     * @param band
     */
    public void addBand(MusicBand band) {
        bands.add(band);
    }

    /**
     * Выводит информацию о коллекции
     *
     * @param id
     * @return
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
     * Удаляет все элементы коллекции
     *
     * @return
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
     * @param id
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
     * @param id
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
     * @return
     */
    public HashSet<MusicBand> getMusicBands() {
        return bands;
    }

    /**
     * Выводит информацию о коллекции
     *
     * @param newColl
     */
    public void setCollection(HashSet<MusicBand> newColl) {
        this.bands = newColl;
    }

    /**
     * Выводит информацию о коллекции
     *
     * @return
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
