package org.example;

import java.util.*;

import org.example.commands.Command;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.exceptions.InvalidDataException;
import org.example.managers.CollectionManager;
import org.example.managers.ConsoleManager;
import org.example.managers.HistoryCollection;
import org.example.functional.Invoker;
import org.example.managers.XMLParser;
import org.example.model.MusicBand;

import javax.xml.bind.JAXBException;


public class Main {
    public static CollectionManager cm = new CollectionManager();

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);

    public static ConsoleManager console = new ConsoleManager();

    public static HistoryCollection hc = new HistoryCollection();

//    public static void main(String[] args) {
//        try {
//            String filePath = "src/main/resources/input.xml";
//            HashSet<MusicBand> musicBands = XMLParser.parseXML(filePath);
//
//            for (MusicBand band : musicBands) {
//                System.out.println(band);
//            }
//        }
//        catch (JAXBException e) {
//            System.out.println(e.getMessage());
//        }
//        catch (NullPointerException e) {
//            System.err.println("Файл input.xml не найден. Проверьте его расположение.");
//        } catch (InvalidDataException e) {
//            System.err.println("Ошибка в данных файла: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
        while (true) {
            System.out.print("Введите команду: ");
            if (!sc.hasNextLine()) {
                break;
            }
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            try {
                String[] tokens = Arrays.stream(line.split(" "))
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);
                console.setTokens(tokens);
                Command command = inv.commands.get(tokens[0]);
                if (tokens.length == 1) {
                    if (command.getArgsAmount() != 0) throw new IncorrectArgsNumber(command.getArgsAmount());
                    command.execute();
                }
                if (tokens.length == 2) {
                    try {
                        if (command.getArgsAmount() == 0) throw new IncorrectArgsNumber(0);
                        command.execute();
                    } catch (IncorrectArgsNumber e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (tokens.length > 2)
                    if (command.getArgsAmount() != tokens.length - 1)
                        throw new IncorrectArgsNumber(command.getArgsAmount());
            } catch (NullPointerException e) {
                System.out.println("Команда неизвестная, введите другую");
            } catch (IncorrectArgsNumber e) {
                System.out.println(e.getMessage());
                System.out.println("Попробуйте ещё раз");

            }
        }
    }
}