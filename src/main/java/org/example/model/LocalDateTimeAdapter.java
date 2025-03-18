package org.example.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Адаптер для преобразования LocalDateTime в строку и обратно.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    /**
     * Формат для преобразования LocalDateTime в строку.
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Преобразует строку с датой и временем в объект {@link LocalDateTime}.
     *
     * @param dateTimeString строка, содержащая дату и время в заданном формате.
     * @return объект {@link LocalDateTime}, соответствующий переданной строке.
     * @throws Exception если строка имеет неверный формат или не может быть разобрана.
     */
    @Override
    public LocalDateTime unmarshal(String dateTimeString) throws Exception {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    /**
     * Преобразует объект в XML-строку (маршаллизация).
     *
     * @param dateTime
     * @return
     * @throws Exception
     */
    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}