package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.Closable;
import ru.se.ifmo.s466351.lab6.server.command.Command;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.command.MovieDataReceiver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class CommandHandler {
    private final CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public ServerResponse handle(ClientCommandRequest request, SelectionKey key) throws IOException {
        String commandName = request.command();
        String argument = request.argument();
        String message = "";

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда не найдена");
        }

        if (command instanceof Closable) {
            SocketChannel channel = (SocketChannel) key.channel();
            channel.close();
            key.cancel();
            return null;
        }

        if (command instanceof MovieDataReceiver) {
            PendingRequest.add((SocketChannel) key.channel(), request);
            return new ServerResponse(ResponseStatus.NEED_MOVIE_DATA, "Введите информацию о фильме");
        }

        try {
            message = command.execute(argument);
            return new ServerResponse(ResponseStatus.OK, message);
        } catch (RuntimeException e) {
            return new ServerResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    public ServerResponse handle(MovieDTO movieDTO, SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ClientCommandRequest originalRequest = PendingRequest.get(channel);
        String commandName = originalRequest.command();
        String argument = originalRequest.argument();
        String message = "";

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда не найдена");
        }

        if (!(command instanceof MovieDataReceiver receiver)) {
            return null;
        }

        try {
            message = receiver.execute(argument, movieDTO);
            return new ServerResponse(ResponseStatus.OK, message);
        } catch (RuntimeException e) {
            return new ServerResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
