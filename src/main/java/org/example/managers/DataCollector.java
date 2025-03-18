package org.example.managers;

import org.example.Main;
import org.example.exceptions.NullValueException;
import org.example.model.*;

import java.util.EnumSet;
import java.util.NoSuchElementException;

/**
 * Класс для сбора данных о музыкальной группе
 */
public class DataCollector {
    /**
     * Обертка для сбора данных
     *
     * @return
     */
    public MusicBand wrap() {
        MusicBand musicBand = new MusicBand();
        try {
            collectName(musicBand);
            collectCoordinates(musicBand);
            collectNumberOfParticipants(musicBand);
            collectMusicGenre(musicBand);
            collectStudio(musicBand);

            System.out.println("Данные успешно собраны");
            return musicBand;
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return null;
    }

    /**
     * Сбор названия группы
     *
     * @param musicBand
     */
    public void collectName(MusicBand musicBand) {
        System.out.println("Введите название группы: ");
        String name = collectString();
        musicBand.setName(name);
    }

    /**
     * Сбор координат
     *
     * @param musicBand
     */
    public void collectCoordinates(MusicBand musicBand) {
        System.out.println("Введите координату x (Integer)");
        Integer x = collectInteger();
        System.out.println("Введите координату y (long)");
        long y = collectAllLong();
        musicBand.setCoordinates(new Coordinates(x, y));
    }

    /**
     * Сбор числа участников
     *
     * @param musicBand
     */
    public void collectNumberOfParticipants(MusicBand musicBand) {
        System.out.println("Введите количество участников группы: ");
        long numberOfParticipants = collectLong();
        musicBand.setNumberOfParticipants(numberOfParticipants);
    }

    /**
     * Сбор музыкального жанра
     *
     * @param musicBand
     */
    public void collectMusicGenre(MusicBand musicBand) {
        System.out.println("Введите музыкальный жанр из списка (или оставьте строку пустой, чтобы установить null): ROCK, JAZZ, PUNK_ROCK");
        MusicGenre type = collectMusicGenre();
        musicBand.setGenre(type);
    }


    /**
     * Сбор студии
     */
    public void collectStudio(MusicBand musicBand) {
        System.out.println("Введите имя студии (или оставьте строку пустой, чтобы установить null):");
        String input = Main.sc.nextLine().trim();
        if (input.isEmpty()) {
            musicBand.setStudio(null);
        } else {
            Studio studio = new Studio();
            studio.setName(input);
            musicBand.setStudio(studio);
        }
    }


    /**
     * Сбор всех Long чисел
     *
     * @return
     */
    public Long collectAllLong() {
        while (true) {
            try {
                long capacity = Long.parseLong(collectValue().trim());
                return capacity;
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            } catch (NumberFormatException ex) {
                System.out.println("Введите тип long, напоминание, что long < " + Long.MAX_VALUE + " и > " + Long.MIN_VALUE);
            }
        }
    }

    /**
     * Сбор всех Long чисел больших 0
     *
     * @return
     */
    public Long collectLong() {
        while (true) {
            try {
                long capacity = Long.parseLong(collectValue().trim());
                if (capacity <= 0) {
                    System.out.println("Значение этого поля должно быть > 0");
                    continue;
                }
                return capacity;
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            } catch (NumberFormatException ex) {
                System.out.println("Введите тип long, напоминание, что long < " + Long.MAX_VALUE + " и > " + Long.MIN_VALUE);
            }
        }
    }

    /**
     * Сбор всех значений
     *
     * @return
     * @throws NullValueException
     * @throws IllegalArgumentException
     */
    public String collectValue() throws NullValueException, IllegalArgumentException {
        String value = Main.sc.nextLine();
        if (value.trim().isEmpty()) {
            throw new NullValueException();
        }
        return value;
    }

    /**
     * Сбор всех строк
     *
     * @return
     */
    public String collectString() {
        while (true) {
            try {
                return collectValue().trim();
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            }
        }
    }

    /**
     * Сбор всех Integer чисел
     *
     * @return
     */
    public Integer collectInteger() {
        while (true) {
            try {
                int capacity = Integer.parseInt(collectValue().trim());
                return capacity;
            } catch (NullValueException ex) {
                System.out.println("Значение этого поля не может быть пустым");
            } catch (IllegalArgumentException ex) {
                System.out.println("Введите тип Integer, напоминание, что Integer < " + Integer.MAX_VALUE + " и > " + Integer.MIN_VALUE);
            }
        }
    }


    /**
     * Сбор всех музыкальных жанров
     *
     * @return
     */
    public MusicGenre collectMusicGenre() {
        while (true) {
            String input = Main.sc.nextLine();
            if (input.trim().isEmpty()) {
                return null;
            }
            try {
                return MusicGenre.valueOf(input.toUpperCase().trim());
            } catch (IllegalArgumentException ex) {
                System.out.println("Введите одно из предложенных значений (пустая строка, ROCK, JAZZ, PUNK_ROCK), не балуйтесь!...");
                EnumSet<MusicGenre> genres = EnumSet.allOf(MusicGenre.class);
                for (MusicGenre musicGenre : genres) {
                    System.out.println(musicGenre);
                }
            }
        }
    }
}