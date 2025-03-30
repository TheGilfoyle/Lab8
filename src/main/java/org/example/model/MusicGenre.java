package org.example.model;

/**
 * Перечисление, представляющее музыкальные жанры.
 * Каждый жанр имеет соответствующее числовое значение.
 */
public enum MusicGenre {
    JAZZ(0),
    PUNK_ROCK(1),
    ROCK(2);
    /**
     * Числовое значение жанра.
     */
    private final int value;

    /**
     * Конструктор перечисления, принимающий числовое значение жанра.
     *
     * @param value числовое значение жанра
     */
    MusicGenre(int value) {
        this.value = value;
    }

    /**
     * Возвращает числовое значение жанра.
     *
     * @return числовое значение жанра
     */
    public int getValue() {
        return value;
    }
}
