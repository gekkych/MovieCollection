package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.Command;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.command.MovieDataReceiver;

public class CommandHandler {
    private final CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public ServerResponse handle(ClientCommandRequest request) {
        String commandName = request.command();
        String argument = request.argument();

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда \"" + commandName + "\" не найдена.");
        }

        if (command instanceof MovieDataReceiver) {
            return new ServerResponse(ResponseStatus.NEED_MOVIE_DATA, "Команда \"" + commandName + "\" требует данные фильма.");
        }

        try {
            String result = command.execute(argument);
            return new ServerResponse(ResponseStatus.OK, result);
        } catch (RuntimeException e) {
            return new ServerResponse(ResponseStatus.ERROR, "Ошибка выполнения: " + e.getMessage());
        }
    }

    public ServerResponse handle(ClientCommandRequest originalRequest, MovieDTO movieDTO) {
        String commandName = originalRequest.command();
        String argument = originalRequest.argument();

        Command command = commandManager.getCommand(commandName);

        if (command == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда \"" + commandName + "\" не найдена.");
        }

        if (!(command instanceof MovieDataReceiver receiver)) {
            return new ServerResponse(ResponseStatus.ERROR, "Команда \"" + commandName + "\" не принимает данные фильма.");
        }

        try {
            String result = receiver.execute(argument, movieDTO);
            return new ServerResponse(ResponseStatus.OK, result);
        } catch (RuntimeException e) {
            return new ServerResponse(ResponseStatus.ERROR, "Ошибка выполнения: " + e.getMessage());
        }
    }
}
