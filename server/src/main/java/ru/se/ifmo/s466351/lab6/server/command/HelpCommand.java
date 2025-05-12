package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HelpCommand extends Command {
    private final HashMap<String, Command> commandMap;

    public HelpCommand(HashMap<String, Command> commandMap) {
        super("help");
        setAccessLevel(Role.GUEST);
        this.commandMap = commandMap;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        StringBuilder result = new StringBuilder();
        List<String> commandList = new ArrayList<>();
        result.append("Доступные команды: ").append("\n");
        for (Command command : commandMap.values()) {
            commandList.add(command.description());
        }
        Collections.sort(commandList);
        for (String description : commandList) {
            result.append(description).append("\n");
        }
        return result.toString();
    }

    @Override
    public String description() {
        return this.getName() + " - справка по доступным командам";
    }
}
