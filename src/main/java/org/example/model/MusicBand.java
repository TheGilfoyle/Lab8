package org.example.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * Класс, представляющий музыкальную группу.
 */
@XmlRootElement
public class MusicBand implements Comparable<MusicBand> {
    /**
     * ID музыкальной группы
     */
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Название музыкальной группы
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты музыкальной группы
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания музыкальной группы
     */
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Количество участников музыкальной группы
     */
    private long numberOfParticipants; //Значение поля должно быть больше 0
    /**
     * Жанр музыкальной группы
     */
    private MusicGenre genre; //Поле может быть null
    /**
     * Студия музыкальной группы
     */
    private Studio studio; //Поле может быть null

    /**
     * Конструктор для создания музыкальной группы
     *
     * @param name название
     * @param coordinates координаты
     * @param numberOfParticipants количество участников
     * @param genre жанр
     * @param studio студия
     */
    public MusicBand(String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre, Studio studio) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDateTime.now();
        this.id = IdGen.generateID();
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.studio = studio;
    }

    /**
     * Конструктор для создания музыкальной группы по умолчанию
     */
    public MusicBand() {
        this.id = IdGen.generateID();
        this.creationDate = java.time.LocalDateTime.now();
    }

    /**
     * Геттер для получения ID музыкальной группы
     *
     * @return Возвращает ID
     */
    @XmlElement
    public int getId() {
        return id;
    }

    /**
     * Сеттер для установки ID музыкальной группы
     *
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Геттер для получения названия музыкальной группы
     *
     * @return название музыкальной группы
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Сеттер для установки названия музыкальной группы
     *
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Геттер для получения координат музыкальной группы
     *
     * @return координаты
     */
    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Сеттер для установки координат музыкальной группы
     *
     * @param coordinates координаты
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Геттер для получения количества участников музыкальной группы
     *
     * @return число участников
     */
    @XmlElement
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * Сеттер для установки количества участников музыкальной группы
     *
     * @param numberOfParticipants количество участников
     */
    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Геттер для получения жанра музыкальной группы
     *
     * @return Музыкальный жанр из enum
     */
    @XmlElement
    public MusicGenre getGenre() {
        return genre;
    }

    /**
     * Сеттер для установки жанра музыкальной группы
     *
     * @param genre музыкальный жанр
     */
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Геттер для получения студии музыкальной группы
     *
     * @return Возвращает студию
     */
    @XmlElement
    public Studio getStudio() {
        return studio;
    }

    /**
     * Сеттер для установки студии музыкальной группы
     *
     * @param studio студия
     */
    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    /**
     * Геттер для получения даты создания музыкальной группы
     *
     * @return Возвращает время создания
     */
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Сеттер для установки даты создания музыкальной группы
     *
     * @param creationDate дата создания
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Метод для получения строки, представляющей музыкальную группу в формате для записи в файл
     */
    @XmlElement(name = "genre")
    private String genreString;

    /**
     * Геттер для получения жанра музыкальной группы в виде строки
     *
     * @return жанр преобразованный в жанр
     */
    @XmlTransient
    public String getGenreString() {
        return genreString;
    }

    /**
     * Имя студии и координаты музыкальной группы в виде строки
     *
     * @return строковое представление в файл
     */
    public String fileToString() {
        return name + "," + coordinates.fileToString();
    }

    /**
     * Переопределение метода toString для вывода музыкальной группы в виде строки
     * @return возвращает строковое представление музыкальной группы
     */
    @Override
    public String toString() {
        return "MusicBand\n" +
                "id=" + id +
                "\ncreationDate= " + creationDate +
                "\nname='" + name + '\'' +
                "\ncoordinates=" + coordinates +
                "\nnumberOfParticipants=" + numberOfParticipants +
                "\ngenre=" + genre +
                "\nstudio=" + studio +
                "\n-------------------------";
    }

    /**
     * Сравнение музыкальных групп по количеству участников
     *
     * @param o объект сравнения, Музыкальная группа
     * @return число
     */
    @Override
    public int compareTo(MusicBand o) {
        return Long.compare(this.numberOfParticipants, o.numberOfParticipants);
    }
}