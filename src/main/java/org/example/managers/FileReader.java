package org.example.managers;

import org.example.usualClasses.MusicBand;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс для чтения пути к XML-файлу и его парсинга.
 */
public class FileReader {

    /**
     * Метод для чтения пути к XML-файлу и вызова парсера.
     */
    public static void readAndParseFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к XML-файлу: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        try {
            HashSet<MusicBand> musicBands = XMLParser.parseXML(filePath);
            System.out.print("Файл успешно обработан. Количество групп: ");
            for (MusicBand musicBand : musicBands) {
                System.out.println("ID: " + musicBand.getId());
                System.out.println("Name: " + musicBand.getName());
                System.out.println("Coordinates: (" + musicBand.getCoordinates().getX() + ", " + musicBand.getCoordinates().getY() + ")");
                System.out.println("LocalDate: " + musicBand.getCreationDate());
                System.out.println("Number of Participants: " + musicBand.getNumberOfParticipants());
                System.out.println("Genre: " + musicBand.getGenre());
                System.out.println("Studio: " + (musicBand.getStudio() != null ? musicBand.getStudio().getName() : "null"));
                System.out.println("-----------------------------");
            }
        } catch (JAXBException e) {
            System.out.println("Ошибка при парсинге XML: " + e.getMessage());
        }
    }
}
