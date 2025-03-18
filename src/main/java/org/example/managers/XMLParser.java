package org.example.managers;

import org.example.exceptions.InvalidDataException;
import org.example.model.MusicBand;
import org.example.model.MusicBandWrapper;
import org.example.model.MusicGenre;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Класс для парсинга XML-файла и преобразования его в коллекцию объектов MusicBand.
 * Класс использует JAXB для преобразования XML-данных в объекты MusicBand.
 * Класс также содержит методы для валидации данных группы и обработки ошибок.
 * Класс также содержит метод для чтения файла XML и преобразования его в коллекцию объектов MusicBand.
 * Класс также содержит метод для получения количества невалидных и валидных элементов.
 * Класс также содержит метод для получения количества элементов в коллекции.
 */
public class XMLParser {
    /**
     * Валидация группы
     *
     * @param band
     * @throws InvalidDataException
     */
    private static void validateMusicBand(MusicBand band) throws InvalidDataException {
        if (band.getName() == null || band.getName().trim().isEmpty()) {
            throw new InvalidDataException("Название группы не может быть пустым... Элемент не добавлен");
        }
        if (band.getCoordinates() == null) {
            throw new InvalidDataException("Координаты не могут быть пустыми... Элемент не добавлен");
        }

        band.getCoordinates().convertCoordinates();

        if (band.getNumberOfParticipants() <= 0) {
            throw new InvalidDataException("Количество участников должно быть больше 0... Элемент не добавлен");
        }

        if (band.getGenreString() != null && !band.getGenreString().trim().isEmpty()) {
            try {
                MusicGenre parsedGenre = MusicGenre.valueOf(band.getGenreString().trim().toUpperCase());
                band.setGenre(parsedGenre);
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Недопустимый музыкальный жанр: " + band.getGenreString() + "... Элемент не добавлен");
            }
        }
    }


    /**
     * Чтение файла XML и преобразование его в коллекцию объектов MusicBand.
     *
     * @param filePath
     * @return
     * @throws JAXBException
     * @throws InvalidDataException
     */
    public static HashSet<MusicBand> parseXML(String filePath) throws JAXBException, InvalidDataException {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandWrapper.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        MusicBandWrapper wrapper;
        try {
            wrapper = (MusicBandWrapper) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new JAXBException("Ошибка при чтении XML-файла: файл может быть поврежден или не соответствует ожидаемой структуре.", e);
        }

        if (wrapper == null || wrapper.getMusicBands() == null) {
            throw new InvalidDataException("Файл не содержит данных или данные не соответствуют ожидаемой структуре.");
        }

        HashSet<MusicBand> musicBands = wrapper.getMusicBands();

        int invalidCount = 0;
        int validCount = 0;

        Iterator<MusicBand> iterator = musicBands.iterator();
        while (iterator.hasNext()) {
            MusicBand band = iterator.next();
            try {
                validateMusicBand(band);
                validCount++;
            } catch (InvalidDataException | IllegalArgumentException e) {
                iterator.remove();
                invalidCount++;
                System.out.println("Ошибка в элементе: " + e.getMessage());
            }
        }

        System.out.println("Количество элементов с ошибками: " + invalidCount);
        System.out.println("Количество успешно добавленных элементов: " + validCount);
        return musicBands;
    }
}
