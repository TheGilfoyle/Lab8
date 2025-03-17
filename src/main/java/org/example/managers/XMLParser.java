package org.example.managers;

import org.example.exceptions.InvalidDataException;
import org.example.usualClasses.MusicBand;
import org.example.usualClasses.MusicBandWrapper;
import org.example.usualClasses.Coordinates;
import org.example.usualClasses.IDgen;
import org.example.usualClasses.MusicGenre;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Класс для парсинга XML-файла и преобразования его в коллекцию объектов MusicBand.
 */
public class XMLParser {
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
                MusicGenre.valueOf(band.getGenreString().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Недопустимый музыкальный жанр: " + band.getGenreString() + "... Элемент не добавлен");
            }
        }
    }



    public static HashSet<MusicBand> parseXML(String filePath) throws JAXBException {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandWrapper.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        MusicBandWrapper wrapper = (MusicBandWrapper) jaxbUnmarshaller.unmarshal(file);
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
