package org.example.managers;

import org.example.usualClasses.MusicBand;

import java.time.LocalDateTime;
import java.util.*;


/**
 * The type Collection manager.
 */
public class CollectionManager {
    /**
     * The collection
     */
    HashSet<MusicBand> bands = new HashSet<>();

    private long lastId = 0;

    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Instantiates a new Collection manager.
     */
    public CollectionManager() {
    }

    /**
     * Add music band to collection.
     */
    public void addBand(MusicBand band) {
        bands.add(band);
    }

    /**
     * Get new id long. Automatically generation.
     *
     * @return the long
     */
    public long getNewId(){
        return ++lastId;
    }

    /**
     * Get music band by ID of music band.
     *
     * @param id the ID
     * @return the music band
     */
    public MusicBand getMusicBandByID(long id){
        for (MusicBand musicBand : bands) {
            if (musicBand.getId() == id) {
                return musicBand;
            }
        }
        System.out.println("Элемента с таким id не обнаружено");
        return null;
    }

//    public int getMin(){
//        if(bands.size() == 0)
//            System.out.println("Коллекция пуста");
//            return 0;
//
//        else{
//            int min =
//            for (MusicBand musicBand : bands) {
//                musicBand
//            }
//        }
//
//    }

    /**
     * Remove music band from collection by ID.
     *
     * @param id the ID
     */
    public void removeByID(long id){
        try {
            bands.remove(getMusicBandByID(id));
        }catch (IndexOutOfBoundsException ex){
            System.out.println("Введите значение long >=0");
        }
    }

//    public void updateID(long id){
//        try {
//            bands.remove(getMusicBandByID(id));
//        }catch (IndexOutOfBoundsException ex){
//            System.out.println("Введите значение long >=0");
//        }
//    }

    /**
     * Get music band's collection.
     *
     * @return the hashSet
     */
    public HashSet<MusicBand> getMusicBands(){
        return bands;
    }

    /**
     * Set new collection.
     *
     * @param newColl the new coll
     */
    public void setCollection(HashSet<MusicBand> newColl){
        this.bands = newColl;
        lastId = getNewId();
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (MusicBand band : bands) {
            s.append(band.fileToString()).append("\n");
        }
        return s.toString();
    }

    public void info(){
        System.out.println("HashSet, " + creationDate.toString() + ", " + bands.size());
    }
    /**
     * Get last generate id.
     *
     * @return the last id
     */
    public long getLastId() {
        return lastId;
    }

    /**
     * Set new last id, if last element has deleted.
     *
     * @param lastId the last id
     */
    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}