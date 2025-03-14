package org.example.functional;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public Map<String, Command> commands = new HashMap<>();
    public Map<String, Command> commandsWithNoArgs = new HashMap<>();

    public Invoker() {
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update_id", new UpdateID());
        commands.put("remove_by_id", new RemoveByID());
        commands.put("clear", new Clear());
        commands.put("save", new Save());
        commands.put("execute_script", new ExecuteScript());
        commands.put("exit", new Exit());
        commands.put("remove_lower", new RemoveLower());
        commands.put("add_if_min", new AddIfMin());
        commands.put("count_by_studio", new CountByStudio());
        commands.put("count_greater_than_genre", new CountGreaterThanGenre());
        commands.put("filter_less_than_studio", new FilterLessThanStudio());
        commands.put("history", new History());

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            if (entry.getValue().getArgsAmount() == 0) {
                commandsWithNoArgs.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Map<String, Command> getCommandsWithNoArgs() {
        return commandsWithNoArgs;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
