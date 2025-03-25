package org.example.commands;

import org.example.Main;
import org.example.exceptions.IncorrectArgsNumber;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static org.example.Main.inv;

/**
 * Команда для выполнения скрипта из файла.
 */
public class ExecuteScript extends Command {
    /**
     * Конструктор класса ExecuteScript.
     */
    public ExecuteScript() {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", 1);
    }

    /**
     * Метод для выполнения команды.
     */
    @Override
    public void execute() {
        try {
            String add = "src/main/resources/";//TODO
            String file = Main.console.getToken(1);
            String fileName = add + file;
            InputStream previousInput = System.in;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                System.setIn(new FileInputStream(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (inv.getCommands().containsKey(line)) {
                        System.out.println("Выполнение команды: " + line);
                    } else {
                        continue;
                    }

                    String[] tokens = Arrays.stream(line.split(" "))
                            .filter(s -> !s.isEmpty())
                            .toArray(String[]::new);

                    Main.console.setTokens(tokens);

                    Command command = inv.commands.get(tokens[0]);
                    if (command == null) {
                        System.out.println("Команда неизвестная: " + tokens[0]);
                        continue;
                    }

                    if (tokens.length - 1 != command.getArgsAmount()) {
                        System.out.println("Неверное количество аргументов для команды: " + tokens[0]);
                        continue;
                    }

                    try {
                        command.execute();
                    } catch (IncorrectArgsNumber e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }finally {
                System.setIn(previousInput);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указано имя файла для выполнения скрипта.");
        }
    }
}