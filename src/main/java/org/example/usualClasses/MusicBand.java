package org.example.usualClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

/**
 * Класс, представляющий музыкальную группу.
 */
@XmlRootElement
public class MusicBand implements Comparable<MusicBand>{
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Studio studio; //Поле может быть null

    public MusicBand(String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre, Studio studio) {
        this.name = name;
        this.coordinates = coordinates;
        this.id=IDgen.generateID();
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.studio = studio;
    }

    public MusicBand() {
        this.id = IDgen.generateID();
    }

    public MusicBand(boolean bool){
        this.creationDate = java.time.LocalDateTime.now();
    }

    // Геттеры и сеттеры

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @XmlElement
    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    @XmlElement
    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public LocalDateTime getLocalCreationDate() {
        return creationDate;
    }

    public void setLocalCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String fileToString() {
        return name + "," + coordinates.fileToString();
    }

    @Override
    public String toString() {
        return "MusicBand\n" +
                "id=" + id +
                "\nname='" + name + '\'' +
                "\ncoordinates=" + coordinates +
                "\ncreationDate=" + creationDate +
                "\nnumberOfParticipants=" + numberOfParticipants +
                "\ngenre=" + genre +
                "\nstudio=" + studio +
                "\n-------------------------";
    }

    @Override
    public int compareTo(MusicBand o) {
        return Long.compare(this.numberOfParticipants, o.numberOfParticipants);
    }
}