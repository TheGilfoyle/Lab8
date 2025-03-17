package org.example.usualClasses;

import org.example.exceptions.InvalidDataException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

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

    @XmlElement(name = "x")
    private String xString;

    @XmlElement(name = "y")
    private String yString;

    public void convertCoordinates() throws InvalidDataException {
        if (xString == null || xString.trim().isEmpty()) {
            throw new InvalidDataException("Координата X не может быть пустой... Элемент не добавлен");
        }
        if (yString == null || yString.trim().isEmpty()) {
            throw new InvalidDataException("Координата Y не может быть пустой... Элемент не добавлен");
        }

        try {
            this.x = Integer.parseInt(xString.trim());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Координата X должна быть числом... Элемент не добавлен");
        }

        try {
            this.y = Long.parseLong(yString.trim());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Координата Y должна быть числом... Элемент не добавлен");
        }
    }

    @XmlTransient
    public String getXString() {
        return xString;
    }

    @XmlTransient
    public String getYString() {
        return yString;
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