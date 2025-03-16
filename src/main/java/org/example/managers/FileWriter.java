package org.example.managers;

import org.example.usualClasses.MusicBand;
import org.example.usualClasses.MusicBandWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

public class FileWriter {
    private static final String FILE_PATH = "resources/musicBands.xml";

    public static void writeToFile(HashSet<MusicBand> musicBands) {
        File file = new File(FILE_PATH);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            MusicBandWrapper wrapper = new MusicBandWrapper();
            wrapper.setMusicBands(musicBands);
            marshaller.marshal(wrapper, fos);

            System.out.println("Файл успешно записан в " + FILE_PATH);
        } catch (JAXBException e) {
            System.out.println("Ошибка при сериализации XML: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
