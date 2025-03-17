package org.example.usualClasses;

import javax.xml.bind.annotation.XmlElement;

/**
 * Класс, представляющий координаты.
 */
public class Coordinates {
    private Integer x;
    private long y;


    public Coordinates() {
    }

    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;
    }

    @XmlElement
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @XmlElement
    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String fileToString() {
        return x + "," + y;
    }

    @Override
    public String toString() {
        return " {" +
                "\n      x=" + x +
                "\n      y=" + y + "\n}";
    }
}