package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.dto.DTO;
import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.dto.UserDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.Closable;
import ru.se.ifmo.s466351.lab6.server.command.Command;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.command.Receiver;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;

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

        ClientContext context = (ClientContext) key.attachment();
        if (!context.getRole().hasAccess(command.getAccessLevel())) {
            return new ServerResponse(ResponseStatus.ERROR, "Нет доступа к команде");
        }

        if (command instanceof Closable) {
            SocketChannel channel = (SocketChannel) key.channel();
            channel.close();
            key.cancel();
            return null;
        }

        if (command instanceof Receiver<?> receiver) {
            PendingRequest.add((SocketChannel) key.channel(), request);
            if (receiver.getType() == MovieDTO.class) {
                return new ServerResponse(ResponseStatus.NEED_MOVIE_DATA, "Введите информацию о фильме");
            }
            if (receiver.getType() == UserDTO.class) {
                return new ServerResponse(ResponseStatus.NEED_USER_DATA, "Введите данные пользователя.");
            }
            PendingRequest.remove((SocketChannel) key.channel());
            return new ServerResponse(ResponseStatus.ERROR, "Неизвестный тип.");
        }

        try {
            message = command.execute(argument, key);
            return new ServerResponse(ResponseStatus.OK, message);
        } catch (RuntimeException e) {
            return new ServerResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    public <T extends DTO> ServerResponse handle(T data, SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ClientCommandRequest originalRequest = PendingRequest.get(channel);
        String commandName = originalRequest.command();
        String argument = originalRequest.argument();
        String message = "";

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда не найдена");
        }

        if (command instanceof Receiver<?> receiver && receiver.getType().isInstance(data)) {
            Receiver<T> typedReceiver = (Receiver<T>) receiver;
            message = typedReceiver.execute(argument, data, key);
            return new ServerResponse(ResponseStatus.OK, message);
        }
        return new ServerResponse(ResponseStatus.ERROR, "Неизвестный тип.");
    }
}
