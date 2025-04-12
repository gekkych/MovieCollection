package ru.se.ifmo.s466351.lab6.common.request;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;

public record ClientMovieDataRequest(RequestStatus status, MovieDTO movieDTO) {}
