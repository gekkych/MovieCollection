package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.Server;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.command.MovieDataReceiver;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class RequestRouter {
    CommandHandler commandHandler;
    public RequestRouter(CommandManager commandManager) {
        CommandHandler commandHandler = new CommandHandler(commandManager);
    }
    public ServerResponse route(Request request, SelectionKey key) throws IOException {
        if (request == null) return new ServerResponse(ResponseStatus.ERROR, "Пустой запрос");
        if (request.getStatus() == RequestStatus.ERROR) return new ServerResponse(ResponseStatus.OK, "Запрос с ошибкой проигнорирован");

        if (request instanceof ClientCommandRequest) {
            return commandHandler.handle((ClientCommandRequest) request, key);
        }
        if (request instanceof ClientMovieDataRequest) {
            return commandHandler.handle(((ClientMovieDataRequest) request).movieData(), key);
        }
        return new ServerResponse(ResponseStatus.ERROR, "Неизвестная ошиюка");
    }
}
