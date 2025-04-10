package ru.se.ifmo.s466351.lab6.client.request;

import ru.se.ifmo.s466351.lab6.common.util.Request;

public record CommandRequest(String commandName, String commandArgument) implements Request {
    public Request requestFromClient() {
        return new CommandRequest(commandName, commandArgument); //Тут будет логика запроса
    }
}
