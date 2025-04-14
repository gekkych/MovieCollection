package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.client.input.MovieFieldInput;
import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class ResponseRouter {
    public static Request route(ServerResponse response) {
        if (response == null) throw new ResponseRouterException("Ответ сервера это null");
        if (response.status() == ResponseStatus.ERROR) throw new ResponseRouterException("Ошибка с сервера: " + response.message());

        if(response.status() == ResponseStatus.OK) {
            return CommandRequestInput.inputCommandRequest();
        }
        if (response.status() == ResponseStatus.NEED_MOVIE_DATA) {
            MovieDTO movie = MovieFieldInput.inputMovieData();
            return new ClientMovieDataRequest(RequestStatus.OK, movie);
        }
        throw new ResponseRouterException("Неизвестный статус ответа.");
    }
}
