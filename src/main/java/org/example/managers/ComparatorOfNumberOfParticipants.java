package org.example.managers;

import org.example.usualClasses.MusicBand;

import java.util.Comparator;

public class ComparatorOfNumberOfParticipants implements Comparator<MusicBand> {
    public int compare(MusicBand o1, MusicBand o2) {
        return (int) (o1.getNumberOfParticipants()-o2.getNumberOfParticipants());
    }
}
