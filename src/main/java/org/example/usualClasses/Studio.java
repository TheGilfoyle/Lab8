package org.example.usualClasses;

import javax.xml.bind.annotation.XmlElement;

/**
 * Класс, представляющий студию.
 */
public class Studio {
    private String name;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " " + this.getName();
    }
}