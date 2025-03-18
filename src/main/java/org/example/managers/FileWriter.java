package org.example.managers;

import org.example.model.MusicBand;
import org.example.model.MusicBandWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

/**
 * Класс для записи коллекции в файл.
 */
public class FileWriter {
    /**
     * Конструктор по умолчанию.
     */
    public FileWriter() {
    }
    /**
     * Путь к файлу.
     * Это поле должно быть изменено в зависимости от желаемого пути к файлу.
     */
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/musicBands.xml";

    /**
     * Метод для записи коллекции в файл.
     *
     * @param musicBands коллекция для записи
     */
    public static void writeToFile(HashSet<MusicBand> musicBands) {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();

        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

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
