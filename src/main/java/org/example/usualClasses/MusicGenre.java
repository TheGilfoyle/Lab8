package org.example.usualClasses;

/**
 * Перечисление, представляющее музыкальные жанры.
 */
public enum MusicGenre {
    JAZZ(0),
    PUNK_ROCK(1),
    ROCK(2);

    private final int value;

    MusicGenre(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
