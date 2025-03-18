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
     * @return
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя студии.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает строковое представление студии.
     *
     * @return
     */
    @Override
    public String toString() {
        return " " + this.getName();
    }
}