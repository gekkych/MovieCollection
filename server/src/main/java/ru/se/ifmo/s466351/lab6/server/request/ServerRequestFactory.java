package ru.se.ifmo.s466351.lab6.server.request;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.util.Request;

public class ServerRequestFactory {
    public Request create(String message) {
        return new MovieDTORequest(message);
    }
}
