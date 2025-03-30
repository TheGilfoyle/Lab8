package org.example.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Класс, представляющий студию.
 */
public class Studio {
    /**
     * Имя студии.
     */
    private String name;

    /**
     * Возвращает имя студии.
     *
     * @return имя студии
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя студии.
     *
     * @param name имя студии
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает строковое представление студии.
     *
     * @return строковое представление студии
     */
    @Override
    public String toString() {
        return " " + this.getName();
    }
}