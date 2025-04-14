package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.*;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class RequestRouter {
    CommandHandler commandHandler;
    public RequestRouter(CommandManager commandManager) {
        this.commandHandler = new CommandHandler(commandManager);
    }
    public ServerResponse route(Request request, SelectionKey key) throws IOException {
        if (request == null) return new ServerResponse(ResponseStatus.ERROR, "Пустой запрос");
        System.out.println(request);

        if (request instanceof ClientStatusRequest status) {
            if (status.getStatus() == RequestStatus.PING) {
                return new ServerResponse(ResponseStatus.OK, "Успешное подключение");
            } else if (status.getStatus() == RequestStatus.ERROR) {
                return new ServerResponse(ResponseStatus.OK, "Запрос с ошибкой проигнорирован");
            }
        }

        if (request instanceof ClientCommandRequest) {
            if (((ClientCommandRequest) request).command().equalsIgnoreCase("save")) {
                return new ServerResponse(ResponseStatus.ERROR, "Команда \"save\" недоступна пользователю");
            }
            return commandHandler.handle((ClientCommandRequest) request, key);
        }
        if (request instanceof ClientMovieDataRequest) {
            return commandHandler.handle(((ClientMovieDataRequest) request).movieData(), key);
        }
        return new ServerResponse(ResponseStatus.ERROR, "Неизвестная ошибка");
    }
}
