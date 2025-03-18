package org.example.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

/**
 * Класс-обертка для коллекции объектов MusicBand.
 * Используется для сериализации и десериализации коллекции в XML-файл.
 */
@XmlRootElement(name = "musicBands")
public class MusicBandWrapper {
    /**
     * Хранит коллекцию объектов MusicBand.
     */
    private HashSet<MusicBand> musicBands;

    /**
     * Получает коллекцию объектов MusicBand.
     *
     * @return
     */
    @XmlElement(name = "musicBand")
    public HashSet<MusicBand> getMusicBands() {
        return musicBands;
    }

    /**
     * Устанавливает коллекцию объектов MusicBand.
     *
     * @param musicBands
     */
    public void setMusicBands(HashSet<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }
}