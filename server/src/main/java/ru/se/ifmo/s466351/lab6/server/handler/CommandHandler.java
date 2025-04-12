package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.Command;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.command.MovieDataReceiver;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String handle(ClientCommandRequest request, SocketChannel channel) {
        String commandName = request.command();
        String argument = request.argument();

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return null;
        }

        if (command instanceof MovieDataReceiver) {
            return null;
        }

        try {
            return command.execute(argument);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public String handle(MovieDTO movieDTO, SocketChannel channel) {
        ClientCommandRequest originalRequest = PendingRequest.get(channel);
        String commandName = originalRequest.command();
        String argument = originalRequest.argument();

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return "Команда не найден";
        }

        if (!(command instanceof MovieDataReceiver receiver)) {
            return null;
        }

        try {
            return receiver.execute(argument, movieDTO);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
