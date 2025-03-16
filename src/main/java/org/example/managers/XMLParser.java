package org.example.managers;

import org.example.exceptions.InvalidDataException;
import org.example.usualClasses.IDgen;
import org.example.usualClasses.MusicBand;
import org.example.usualClasses.MusicBandWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashSet;

/**
 * Класс для парсинга XML-файла и преобразования его в коллекцию объектов MusicBand.
 */
public class XMLParser {
    private static void validateMusicBand(MusicBand band) throws InvalidDataException {
        if (band.getName() == null || band.getName().trim().isEmpty()) {
            throw new InvalidDataException("Название группы не может быть пустым... Элемент не добавлен");
        }
        if (band.getCoordinates() == null || band.getCoordinates().getX() == null) {
            throw new InvalidDataException("Координаты не могут быть пустыми... Элемент не добавлен");
        }
        if (band.getNumberOfParticipants() <= 0) {
            throw new InvalidDataException("Количество участников должно быть больше 0... Элемент не добавлен");
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

        for (MusicBand band : musicBands) {
            try {
                validateMusicBand(band);
                musicBands.add(band);
                validCount++;
            } catch (InvalidDataException e) {
                invalidCount++;
                musicBands.remove(band);
                System.out.println("Ошибка в элементе: " + e.getMessage());
            }
        }
        System.out.println("Количество элементов с ошибками: " + invalidCount);
        System.out.println("Количество успешно добавленных элементов: " + validCount);
        return musicBands;
    }


}