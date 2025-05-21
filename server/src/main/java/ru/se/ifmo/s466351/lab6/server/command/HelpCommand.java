package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        return commandMap.values().stream()
                .filter(command ->
                        !context.isAuthenticated() ||
                        (!command.getName().equalsIgnoreCase("login") &&
                        !command.getName().equalsIgnoreCase("register"))
                )
                .filter(command -> context.getRole().hasAccess(command.getAccessLevel()))
                .map(Command::description)
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String description() {
        return this.getName() + " - справка по доступным командам";
    }
}
