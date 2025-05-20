package org.example.managers;

import org.example.model.MusicBand;

import java.util.Comparator;

/**
 * Класс для сравнения количества участников группы.
 */
public class ComparatorOfNumberOfParticipants implements Comparator<MusicBand> {
    /**
     * Конструктор класса
     */
    public ComparatorOfNumberOfParticipants() {
        super();
    }

    /**
     * Сравнивает два объекта типа {@link MusicBand} по количеству участников группы.
     *
     * @param o1 первый объект для сравнения
     * @param o2 второй объект для сравнения
     * @return отрицательное число, если количество участников группы первого объекта меньше
     */
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Long.compare(o1.getNumberOfParticipants(), o2.getNumberOfParticipants());
    }
}