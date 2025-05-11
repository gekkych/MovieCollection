package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.client.input.AuthRequestInput;
import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.client.input.MovieFieldInput;
import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class ResponseRouter {
    public static Request route(ServerResponse response) {
        if (response == null) throw new ResponseRouterException("Ответ сервера это null");
        switch(response.status()) {
            case OK:
                System.out.println(response.message());
                return CommandRequestInput.inputCommandRequest();
            case NEED_MOVIE_DATA:
                System.out.println(response.message());
                MovieDTO movie = MovieFieldInput.inputMovieData();
                return new ClientMovieDataRequest(RequestStatus.OK, movie);
            case NOT_AUTHENTICATED:
                System.out.println(response.message());
                return AuthRequestInput.inputAuthRequest();
            case ERROR:
                throw new ResponseRouterException("Ошибка с сервера: " + response.message());
            default:
                throw new ResponseRouterException("Неизвестный статус ответа.");
        }
    }
}
