package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.*;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class RequestRouter {
    private final CommandHandler commandHandler;

    public RequestRouter(CommandManager commandManager, UserCollection users) {
        this.commandHandler = new CommandHandler(commandManager);
    }

    public ServerResponse route(Request request, SelectionKey key) throws IOException {
        if (request == null) return new ServerResponse(ResponseStatus.ERROR, "Пустой запрос");
        System.out.println(request);
        if (!(key.attachment() instanceof ClientContext context)) return new ServerResponse(ResponseStatus.ERROR, "Пустой запрос");

        //if (!context.isAuthenticated()) return new ServerResponse(ResponseStatus.ERROR, "Нет доступа. Войдите или зарегистрируйтесь.");

        if (request instanceof ClientStatusRequest status) {
            return switch(status.getStatus()) {
                case PING -> new ServerResponse(ResponseStatus.OK, "Успешное подключение");
                case ERROR -> new ServerResponse(ResponseStatus.OK, "Запрос с ошибкой проигнорирован");
                case OK -> new ServerResponse(ResponseStatus.OK, "Всё в порядке");
            };
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

        if (request instanceof ClientUserDataRequest) {
            return commandHandler.handle(((ClientUserDataRequest) request).userData(), key);
        }

        return new ServerResponse(ResponseStatus.ERROR, "Неизвестная ошибка");
    }
}
