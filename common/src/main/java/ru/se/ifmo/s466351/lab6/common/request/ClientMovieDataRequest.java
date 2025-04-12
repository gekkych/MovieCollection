package ru.se.ifmo.s466351.lab6.common.request;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;

public record ClientMovieDataRequest(ResponseStatus status, MovieDTO movieData) implements Request {
    public static RequestType type = RequestType.MOVIE;

    @Override
    public RequestType getType() {
        return type;
    }
}
