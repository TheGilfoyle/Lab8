package org.example.commands;

import org.example.Main;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.managers.ScriptManager;

import java.io.*;
import java.util.Arrays;
import java.util.List;


/**
 * Команда для выполнения скрипта из файла.
 */
public class ExecuteScript extends Command {
    List<String> list = Arrays.asList("add", "add_if_min", "update_id", "count_greater_than_genre", "remove_by_id", "count_by_studio", "remove_lower", "filter_less_than_studio");
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
        Main.scriptMode = true;
        try {
            String file = Main.console.getToken(1);

            ScriptManager scriptManager = new ScriptManager(file);

            try (BufferedReader reader = scriptManager.getBufferedReader()) {
                Main.currentScriptReader = reader;
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    String[] tokens = Arrays.stream(line.split(" "))
                            .filter(s -> !s.isEmpty())
                            .toArray(String[]::new);

                    Main.console.setTokens(tokens);

                    Command command = Main.inv.commands.get(tokens[0]);
                    if (command == null) {
                        continue;
                    }

                    if ("execute_script".equals(command.getNameOfCommand())) {
                        break;
                    }

                    if (tokens.length - 1 != command.getArgsAmount()) {
                        continue;
                    }


                    try {
                        if (command.check(Arrays.copyOfRange(tokens, 1, tokens.length))) {
                            System.out.println("Выполнение команды: " + line);
                        }
                        if(list.contains(tokens[0])) {
                            command.execute(Arrays.copyOfRange(tokens, 1, tokens.length));
                        }
                        else {
                            command.execute();
                        }
                    } catch (IncorrectArgsNumber e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                    }
                }
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указано имя файла для выполнения скрипта.");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении скрипта: " + e.getMessage());
        } finally {
            Main.scriptMode = false;
            Main.currentScriptReader = null;
        }
    }
}