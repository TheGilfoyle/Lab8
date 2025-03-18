package org.example.model;

import org.example.exceptions.InvalidDataException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Класс, представляющий координаты.
 */
public class Coordinates {
    /**
     * Координата X
     */
    private Integer x;
    /**
     * Координата Y
     */
    private long y;

    /**
     * Конструктор по умолчанию.
     */
    public Coordinates() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param x
     * @param y
     */
    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получение координаты X.
     *
     * @return
     */
    @XmlElement
    public Integer getX() {
        return x;
    }

    /**
     * Установка координаты X.
     *
     * @param x
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Получение координаты Y.
     *
     * @return
     */
    @XmlElement
    public long getY() {
        return y;
    }

    /**
     * Установка координаты Y.
     *
     * @param y
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * Поле для хранения строкового значения координаты X.
     */
    @XmlElement(name = "x")
    private String xString;
    /**
     * Поле для хранения строкового значения координаты Y.
     */
    @XmlElement(name = "y")
    private String yString;

    /**
     * Конвертация строковых значений координат в числовые значения.
     *
     * @throws InvalidDataException
     */
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

    /**
     * Получение строкового значения координаты X.
     *
     * @return
     */
    @XmlTransient
    public String getXString() {
        return xString;
    }

    /**
     * Получение строкового значения координаты Y.
     *
     * @return
     */
    @XmlTransient
    public String getYString() {
        return yString;
    }

    /**
     * Перевод координат в строку для файла.
     *
     * @return
     */
    public String fileToString() {
        return x + "," + y;
    }

    /**
     * Перевод координат в строку для файла.
     *
     * @return
     */
    @Override
    public String toString() {
        return " {" +
                "\n      x=" + x +
                "\n      y=" + y + "\n}";
    }
}