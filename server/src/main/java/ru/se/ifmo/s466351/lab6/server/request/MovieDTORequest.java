package ru.se.ifmo.s466351.lab6.server.request;

import ru.se.ifmo.s466351.lab6.common.util.Request;

public record MovieDTORequest(String message) implements Request {
    public Request requestFromClient() {
        return new MovieDTORequest(message); //Тут будет логика запроса
    }
}
