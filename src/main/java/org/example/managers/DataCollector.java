package org.example.managers;

import org.example.Main;
import org.example.exceptions.NullValueException;
import org.example.usualClasses.Coordinates;
import org.example.usualClasses.MusicBand;
import org.example.usualClasses.MusicGenre;
import org.example.usualClasses.Studio;

import java.time.LocalDateTime;
import java.util.EnumSet;

public class DataCollector {
    public MusicBand wrap(){
        MusicBand musicBand = new MusicBand();
        collectName(musicBand);
        collectCoordinates(musicBand);
        collectNumberOfParticipants(musicBand);
        collectMusicGenre(musicBand);
        collectStudio(musicBand);
        System.out.println("Данные успешно собраны");
        return musicBand;
    }
    /**
     * Collect name.
     *
     */
    public void collectName(MusicBand musicBand){
        System.out.println("Введите название группы: ");
        String name = collectString();
        musicBand.setName(name);
    }

    /**
     * Collect coordinates.
     *
     */
    public void collectCoordinates(MusicBand musicBand){
        System.out.println("Введите координату x (Integer)");
        Integer x = collectInteger();
        System.out.println("Введите координату y (long)");
        long y = collectAllLong();
        musicBand.setCoordinates(new Coordinates(x, y));
    }

    /**
     * Collect coordinates.
     *
     */
    public void collectNumberOfParticipants(MusicBand musicBand){
        System.out.println("Введите количество участников группы: ");
        long numberOfParticipants = collectLong();
        musicBand.setNumberOfParticipants(numberOfParticipants);
    }
    /**
     * Collect genre.
     *
     */
    public void collectMusicGenre(MusicBand musicBand){
        System.out.println("Введите музыкальный жанр из списка: ROCK, JAZZ, PUNK_ROCK ");
        MusicGenre type = collectMusicGenre();
        musicBand.setGenre(type);
    }

    /**
     * Collect name.
     *
     */
    public void collectStudio(MusicBand musicBand){
        System.out.println("Введите имя студии");
        Studio studio = new Studio();
        String studioName = collectString();
        studio.setName(studioName);
        musicBand.setStudio(studio);
    }

    /**
     * Collect Long from string.
     *
     * @return the long value
     */
    public Long collectAllLong() {
        while (true) {
            try {
                long capacity = Long.parseLong(collectValue().trim());
                return capacity;
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            } catch (NumberFormatException ex) {
                System.out.println("Введите тип long.");
            }
        }
    }

    /**
     * Collect Long from string.
     *
     * @return the long value
     */
    public Long collectLong() {
        while (true) {
            try {
                long capacity = Long.parseLong(collectValue().trim());
                if (capacity <=0) {
                    System.out.println("Значение этого поля должно быть > 0");
                    continue;
                }
                return capacity;
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            } catch (NumberFormatException ex) {
                System.out.println("Введите тип long.");
            }
        }
    }




    public String collectValue() throws NullValueException, IllegalArgumentException{
        String value = Main.sc.nextLine();
        if(value.trim().isEmpty()){
            throw new NullValueException();
        }
        return value;
    }

    /**
     * Collect String from console
     *
     * @return the string
     */
    public String collectString(){
        while(true){
            try {
                return collectValue().trim();
            }catch (NullValueException ex){
                System.out.println("Значение этого поля не может быть пустым");
            }
        }
    }

    /**
     * Collect Integer from string.
     *
     * @return the integer
     */
    public Integer collectInteger(){
        while(true){
            try {
                int capacity = Integer.parseInt(collectValue().trim());
                return capacity;
            }catch (NullValueException ex){
                System.out.println("Значение этого поля не может быть пустым");
            }catch(IllegalArgumentException ex){
                System.out.println("Введите тип integer, ");
            }
        }
    }


    /**
     * Collect ticket type.
     *
     * @return the ticket type
     */
    public MusicGenre collectMusicGenre(){
        while(true){
            try{
                return MusicGenre.valueOf(collectValue().toUpperCase().trim());
            }catch(NullValueException ex){
                System.out.println("Значение этого поля не может быть пустым");
            }catch (IllegalArgumentException ex){
                System.out.println("Введите одно из предложенных значений");
                EnumSet<MusicGenre> genres = EnumSet.allOf(MusicGenre.class);
                for (MusicGenre musicGenre:genres) {
                    System.out.println(musicGenre);
                }
            }
        }
    }
}
