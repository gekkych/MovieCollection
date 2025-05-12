package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
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
        ClientContext context = (ClientContext) key.attachment();
        StringBuilder result = new StringBuilder();
        List<String> commandList = new ArrayList<>();
        result.append("Доступные команды: ").append("\n");
        for (Command command : commandMap.values()) {
            if (context.isAuthenticated() &&
                    (command.getName().equalsIgnoreCase("login") ||
                    command.getName().equalsIgnoreCase("register"))) {
                continue;
            }

            if (context.getRole().hasAccess(command.getAccessLevel())) {
                commandList.add(command.description());
            }
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
