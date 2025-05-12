package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

import java.util.HashMap;

public class ResponseRouter {
    private static final HashMap<ResponseStatus, ResponseHandler> handlers = new HashMap<>();

    static {
           handlers.put(ResponseStatus.OK, new OkResponseHandler());
           handlers.put(ResponseStatus.ERROR, new ErrorResponseHandler());
           handlers.put(ResponseStatus.NEED_MOVIE_DATA, new NeedMovieDataResponseHandler());
           handlers.put(ResponseStatus.NEED_USER_DATA, new NeedUserDataResponseHandler());
    }

    public static Request route(ServerResponse response) {
        if (response == null) throw new ResponseRouterException("Ответ сервера это null.");
        ResponseHandler handler = handlers.get(response.status());
        if (handler == null) throw new ResponseRouterException("Неизвестный ответ сервера.");
        return handler.handle(response);
    }
}
