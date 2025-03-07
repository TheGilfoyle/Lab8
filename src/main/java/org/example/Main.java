package org.example;

import java.util.HashSet;
import java.util.Scanner;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.managers.XMLParser;
import org.example.usualClasses.MusicBand;
import org.example.functional.Invoker;

import javax.xml.bind.JAXBException;

import static org.example.managers.FileReader.readAndParseFile;

public class Main {
                public static CollectionManager cm = new CollectionManager();

                public static Invoker inv = new Invoker();

                public static Scanner sc = new Scanner(System.in);
//                Проверка парсера
//                public static void main(String[] args) {
//                    try {
//                        String filePath = "src/main/resources/input.xml";// Укажите путь к вашему XML-файлу
//                        HashSet<MusicBand> musicBands = XMLParser.parseXML(filePath);
//
//                        // Вывод данных на экран для проверки
//                        for (MusicBand band : musicBands) {
//                            System.out.println("ID: " + band.getId());
//                            System.out.println("Name: " + band.getName());
//                            System.out.println("Coordinates: (" + band.getCoordinates().getX() + ", " + band.getCoordinates().getY() + ")");
//                            System.out.println("Number of Participants: " + band.getNumberOfParticipants());
//                            System.out.println("Genre: " + band.getGenre());
//                            System.out.println("Studio: " + (band.getStudio() != null ? band.getStudio().getName() : "null"));
//                            System.out.println("-----------------------------");
//                        }
//                    } catch (JAXBException e) {
//                        e.printStackTrace();
//                    } catch (NullPointerException e) {
//                        System.err.println("Файл input.xml не найден. Проверьте его расположение.");
//                        e.printStackTrace();
//                    }
//                }

                public static void main(String[] args) {
                    Invoker invoker = new Invoker();
                    System.out.print("Введите команду: ");
                    while (sc.hasNext()) {
                        try{
                            String line = sc.nextLine().trim();
                            String[] tokens = line.split(" ");
                            Command command = invoker.commands.get(tokens[0]);
                            if (tokens.length == 2) {
                                command.execute(tokens[1]);
                            } else if (tokens.length == 1){
                                command.execute();
                            }
                            System.out.print("Введите команду: ");
                        } catch (NullPointerException e){
                            System.out.println("Команда неизвестная, введите другую");}
//                        } catch (IncorrectArgsNumber e){
//                            System.out.println(e.getMessage());
//                            System.out.println("Попробуйте ещё раз");
//                        }
                    }
                }
            }