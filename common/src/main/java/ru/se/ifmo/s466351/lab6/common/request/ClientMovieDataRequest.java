package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;

@JsonTypeName("movie")
public record ClientMovieDataRequest(RequestStatus status, MovieDTO movieData) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
