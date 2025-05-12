package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.client.input.MovieFieldInput;
import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class NeedMovieDataResponseHandler implements ResponseHandler {
    @Override
    public Request handle(ServerResponse response) {
        System.out.println(response.message());
        MovieDTO movie = MovieFieldInput.inputMovieData();
        return new ClientMovieDataRequest(RequestStatus.OK, movie);
    }
}
