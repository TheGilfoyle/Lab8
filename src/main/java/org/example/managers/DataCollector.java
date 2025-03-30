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
     * Конструктор класса
     */
    public DataCollector() {
    }
    /**
     * Обертка для сбора данных
     *
     * @return объект с данными
     */
    public MusicBand wrap() {
        if (!Main.scriptMode) {
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
        } else {
            String name = collectString();
            if (name == null) return null;

            Integer x = collectInteger();
            if (x == null) return null;

            Long y = collectLong();
            if (y == null) return null;

            Long members = collectLong();
            if (members == null) return null;

            MusicGenre genre = collectMusicGenre();
            if (Main.scriptMode && genre == null) return null;

            String studio = collectString();
            if (studio == null) return null;

            String frontman = collectString();
            if (frontman == null) return null;

            MusicBand musicBand = new MusicBand();
            collectName(musicBand);
            collectCoordinates(musicBand);
            collectNumberOfParticipants(musicBand);
            collectMusicGenre(musicBand);
            collectStudio(musicBand);
            return musicBand;
        }
    }


    /**
     * Сбор названия группы
     *
     * @param musicBand музыкальная группа
     */
    public void collectName(MusicBand musicBand) {
        System.out.println("Введите название группы: ");
        String name = collectString();
        musicBand.setName(name);
    }

    /**
     * Сбор координат
     *
     * @param musicBand музыкальная группа
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
     * @param musicBand музыкальная группа
     */
    public void collectNumberOfParticipants(MusicBand musicBand) {
        System.out.println("Введите количество участников группы: ");
        long numberOfParticipants = collectLong();
        musicBand.setNumberOfParticipants(numberOfParticipants);
    }

    /**
     * Сбор музыкального жанра
     *
     * @param musicBand музыкальная группа
     */
    public void collectMusicGenre(MusicBand musicBand) {
        System.out.println("Введите музыкальный жанр из списка (или оставьте строку пустой, чтобы установить null): ROCK, JAZZ, PUNK_ROCK");
        MusicGenre type = collectMusicGenre();
        musicBand.setGenre(type);
    }


    /**
     * Сбор студии
     * @param musicBand музыкальная группа
     */
    public void collectStudio(MusicBand musicBand) {
        System.out.println("Введите имя студии (или оставьте строку пустой, чтобы установить null):");
        String input = Main.readLine().trim();
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
     * @return Long число
     */
    public Long collectAllLong() {
        while (true) {
            try {
                return Long.parseLong(collectValue().trim());
            } catch (NullValueException ex) {
                if (!Main.scriptMode) System.out.println("Значение этого поля не может быть пустым");
                else return null;
            } catch (NumberFormatException ex) {
                if (!Main.scriptMode) System.out.println("Введите тип long, напоминание, что long < " + Long.MAX_VALUE + " и > " + Long.MIN_VALUE);
                else return null;
            }
        }
    }

    /**
     * Сбор всех Long чисел больших 0
     *
     * @return Long число
     */
    public Long collectLong() {
        while (true) {
            try {
                long value = Long.parseLong(collectValue().trim());
                if (value <= 0) {
                    if (!Main.scriptMode)
                        System.out.println("Значение этого поля должно быть > 0");
                    if (Main.scriptMode)
                        return null;
                    continue;
                }
                return value;
            } catch (NullValueException ex) {
                if (!Main.scriptMode) System.out.println("Значение этого поля не может быть пустым");
                else return null;
            } catch (NumberFormatException ex) {
                if (!Main.scriptMode) System.out.println("Введите тип long, напоминание, что long < " + Long.MAX_VALUE + " и > " + Long.MIN_VALUE);
                else return null;
            }
        }
    }

    /**
     * Сбор всех значений
     *
     * @return Строка без пробелов в начале и в конце
     * @throws NullValueException Если введен пустой ответ
     * @throws IllegalArgumentException Если введен не long
     */
    public String collectValue() throws NullValueException, IllegalArgumentException {
        String value = Main.readLine();
        if (value.trim().isEmpty()) {
            throw new NullValueException();
        }
        return value;
    }

    /**
     * Сбор всех строк
     *
     * @return Строка без пробелов в начале и в конце
     */
    public String collectString() {
        while (true) {
            try {
                return collectValue().trim();
            } catch (NullValueException ex) {
                if (!Main.scriptMode) System.out.println("Значение этого поля не может быть пустым");
                else return null;
            }
        }
    }

    /**
     * Сбор всех Integer чисел
     *
     * @return Integer число
     */
    public Integer collectInteger() {
        while (true) {
            try {
                return Integer.parseInt(collectValue().trim());
            } catch (NullValueException ex) {
                if (!Main.scriptMode) System.out.println("Значение этого поля не может быть пустым");
                else return null;
            } catch (IllegalArgumentException ex) {
                if (!Main.scriptMode) System.out.println("Введите тип Integer, напоминание, что Integer < " + Integer.MAX_VALUE + " и > " + Integer.MIN_VALUE);
                else return null;
            }
        }
    }


    /**
     * Сбор всех музыкальных жанров
     *
     * @return Музыкальный жанр
     */
    public MusicGenre collectMusicGenre() {
        while (true) {
            String input = Main.readLine();
            if (input.trim().isEmpty()) {
                return null;
            }
            try {
                return MusicGenre.valueOf(input.toUpperCase().trim());
            } catch (IllegalArgumentException ex) {
                if (!Main.scriptMode) {
                    System.out.println("Введите одно из предложенных значений (пустая строка, ROCK, JAZZ, PUNK_ROCK), не балуйтесь!...");
                    EnumSet<MusicGenre> genres = EnumSet.allOf(MusicGenre.class);
                    for (MusicGenre musicGenre : genres) {
                        System.out.println(musicGenre);
                    }
                }
                else return null;
            }
        }
    }
}