package org.example.managers;

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

    /**
     * Метод для парсинга XML-файла и преобразования его в коллекцию объектов MusicBand.
     *
     * @param filePath путь к XML-файлу
     * @return коллекция объектов MusicBand
     * @throws JAXBException если произошла ошибка при парсинге XML
     */
    public static HashSet<MusicBand> parseXML(String filePath) throws JAXBException {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandWrapper.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        MusicBandWrapper wrapper = (MusicBandWrapper) jaxbUnmarshaller.unmarshal(file);
//        HashSet<MusicBand> musicBands = wrapper.getMusicBands();
//        for (MusicBand band : musicBands) {
//            band.init(); // Генерация ID и creationDate после парсинга
//        }
//        MusicBandWrapper x = null;
//        x.setMusicBands(musicBands);
        return wrapper.getMusicBands();
    }
}