package org.example.usualClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

/**
 * Класс-обертка для коллекции объектов MusicBand.
 */
@XmlRootElement(name = "musicBands")
public class MusicBandWrapper {

    private HashSet<MusicBand> musicBands;

    @XmlElement(name = "musicBand")
    public HashSet<MusicBand> getMusicBands() {
        return musicBands;
    }

    public void setMusicBands(HashSet<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }
}