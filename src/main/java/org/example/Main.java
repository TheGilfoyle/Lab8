package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;

import org.example.commands.Command;
import org.example.commands.History;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.managers.CollectionManager;
import org.example.managers.ConsoleManager;
import org.example.managers.HistoryCollection;
import org.example.managers.XMLParser;
import org.example.usualClasses.MusicBand;
import org.example.functional.Invoker;

import javax.xml.bind.JAXBException;

import static org.example.managers.FileReader.readAndParseFile;

public class Main {
    public static CollectionManager cm = new CollectionManager();

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);

    public static ConsoleManager console = new ConsoleManager();

    public static HistoryCollection hc = new HistoryCollection();
//                Проверка парсера
                public static void main(String[] args) {
                    try {
                        String filePath = "src/main/resources/input.xml";// Укажите путь к вашему XML-файлу
                        HashSet<MusicBand> musicBands = XMLParser.parseXML(filePath);

                        // Вывод данных на экран для проверки
                        for (MusicBand band : musicBands) {
//
                            System.out.println(band);
                        }
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        System.err.println("Файл input.xml не найден. Проверьте его расположение.");
                        e.printStackTrace();
                    }
                }

//    public static void main(String[] args) {
//        System.out.print("Введите команду: ");
//        while (sc.hasNext()) {
//            try {
//                String line = sc.nextLine().trim();
//                String[] tokens = line.split(" ");
//                console.setTokens(tokens);
//                Command command = inv.commands.get(tokens[0]);
//                if (tokens.length == 2) {
//                    try {
//                        if (command.getArgsAmount() == 0) throw new IncorrectArgsNumber(0);
//                        command.execute();
//                    } catch (IncorrectArgsNumber e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//                if (tokens.length == 1) {
//                    if (command.getArgsAmount() != 0) throw new IncorrectArgsNumber(command.getArgsAmount());
//                    command.execute();
//                }
//                if (tokens.length > 2)
//                    if (command.getArgsAmount() != tokens.length - 1)
//                        throw new IncorrectArgsNumber(command.getArgsAmount());
//                System.out.print("Введите команду: ");
//            } catch (NullPointerException e) {
//                System.out.println("Команда неизвестная, введите другую");
//                System.out.print("Введите команду: ");
//            } catch (IncorrectArgsNumber e) {
//                System.out.println(e.getMessage());
//                System.out.println("Попробуйте ещё раз");
//                System.out.print("Введите команду: ");
//            }
//        }
//    }
}