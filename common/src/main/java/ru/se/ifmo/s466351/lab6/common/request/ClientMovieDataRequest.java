package ru.se.ifmo.s466351.lab6.common.request;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;

public record ClientMovieDataRequest(RequestStatus status, MovieDTO movieData) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
