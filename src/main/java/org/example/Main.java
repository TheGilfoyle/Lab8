package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import org.example.commands.Command;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.exceptions.InvalidDataException;
import org.example.functional.JSCh;
import org.example.managers.*;
import org.example.functional.Invoker;
import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.MusicGenre;
import org.example.model.Studio;

import javax.xml.bind.JAXBException;


public class Main {
    /**
     * Менеджер коллекции
     */
    public static CollectionManager cm = new CollectionManager();
    /**
     * Инвокер команд
     */
    public static Invoker inv = new Invoker();
    /**
     * Менеджер консоли
     */
    public static ConsoleManager console = new ConsoleManager();
    /**
     * Коллекция истории
     */
    public static HistoryCollection hc = new HistoryCollection();
    /**
     * Менеджер ввода
     */
    public static Scanner inputScanner = new Scanner(System.in);
    /**
     * Режим скрипта
     */
    public static boolean scriptMode = false;
    /**
     * Поток ввода скрипта
     */
    public static BufferedReader currentScriptReader = null;

    public static String currentUser;

    public static DBManager db= new DBManager();

    /**
     * Устанавливает поток ввода
     * @param in поток ввода
     */
    public static void updateInput(InputStream in) {
        System.setIn(in);
        inputScanner = new Scanner(in);
    }

    /**
     * Читает строку
     * @return строка
     */
    public static String readLine() {
        if (!inputScanner.hasNextLine()) {
            throw new NoSuchElementException("Ввод завершён. Нет доступных строк.");
        }
        return inputScanner.nextLine();
    }
    public static void main(String[] args) {
        JSCh jsc = new JSCh();
        try {
            jsc.connectSSH();
            db.connect();
            System.out.println("Успешно подключено к базе данных...");

            boolean authenticated = false;
            while (!authenticated) {
                System.out.print("Введите 'register' для регистрации или 'login' для входа: ");
                String cmd;
                try {
                    cmd = readLine().trim().toLowerCase();
                } catch (NoSuchElementException e) {
                    return;
                }
                if ("register".equals(cmd)) {
                    System.out.print("Логин: ");
                    String login;
                    try {
                        login = readLine().trim();
                    } catch (NoSuchElementException e) {
                        return;
                    }
                    System.out.print("Пароль: ");
                    String password;
                    try {
                        password = readLine().trim();
                    } catch (NoSuchElementException e) {
                        return;
                    }
                    db.registerUser(login, password);
                    if (db.login(login, password)) {
                        currentUser = login;
                        authenticated = true;
                    }
                } else if ("login".equals(cmd)) {
                    System.out.print("Логин: ");
                    String login;
                    try {
                        login = readLine().trim();
                    } catch (NoSuchElementException e) {
                        return;
                    }
                    System.out.print("Пароль: ");
                    String password;
                    try {
                        password = readLine().trim();
                    } catch (NoSuchElementException e) {
                        return;
                    }
                    if (db.login(login, password)) {
                        currentUser = login;
                        authenticated = true;
                    } else {
                        System.out.println("Неверный логин или пароль. Попробуйте снова.");
                    }
                } else {
                    System.out.println("Неизвестная команда. Введите 'register' или 'login'.");
                }
            }
            System.out.println("Успешно авторизован как " + currentUser + "\n");

            while (true) {
                System.out.print("Введите команду: ");
                String line;
                try {
                    line = readLine().trim();
                } catch (NoSuchElementException e) {
                    break;
                }
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
        } catch (Exception e) {
            System.out.println("Ошибка запуска программы: " + e.getMessage());
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("Пожалуйста, укажите путь к XML-файлу как аргумент командной строки.");
//            return;
//        }
//        String filePath = args[0];
//        File file = new File(filePath);
//
//        if (!file.exists() || !file.isFile()) {
//            System.out.println("Файл не найден: " + filePath);
//            return;
//        }
//
//        try {
//            HashSet<MusicBand> musicBands = XMLParser.parseXML(filePath);
//
//            System.out.println("\n Файл успешно обработан. Количество групп: " + musicBands.size());
//            System.out.println("==============================================");
//
//            for (MusicBand musicBand : musicBands) {
//                System.out.println(musicBand);
//                System.out.println("=====================");
//            }
//
//        } catch (JAXBException e) {
//            System.out.println(" Ошибка при парсинге XML: " + e.getMessage());
//        } catch (InvalidDataException e) {
//            System.out.println(" Ошибка в данных файла: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println(" Неизвестная ошибка: " + e.getMessage());
//        }
//
//    }
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


//    public static void main(String[] args) {
//        while (true) {
//            System.out.print("Введите команду: ");
//            String line;
//            try {
//                line = readLine().trim();
//            } catch (NoSuchElementException e) {
//                break;
//            }
//            if (line.isEmpty()) {
//                continue;
//            }
//            try {
//                String[] tokens = Arrays.stream(line.split(" "))
//                        .filter(s -> !s.isEmpty())
//                        .toArray(String[]::new);
//                console.setTokens(tokens);
//                Command command = inv.commands.get(tokens[0]);
//                if (tokens.length == 1) {
//                    if (command.getArgsAmount() != 0) throw new IncorrectArgsNumber(command.getArgsAmount());
//                    command.execute();
//                }
//                if (tokens.length == 2) {
//                    try {
//                        if (command.getArgsAmount() == 0) throw new IncorrectArgsNumber(0);
//                        command.execute();
//                    } catch (IncorrectArgsNumber e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//                if (tokens.length > 2)
//                    if (command.getArgsAmount() != tokens.length - 1)
//                        throw new IncorrectArgsNumber(command.getArgsAmount());
//            } catch (NullPointerException e) {
//                System.out.println("Команда неизвестная, введите другую");
//            } catch (IncorrectArgsNumber e) {
//                System.out.println(e.getMessage());
//                System.out.println("Попробуйте ещё раз");
//
//            }
//        }
//    }
}