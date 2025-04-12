package ru.se.ifmo.s466351.lab6.common.request;

public record ClientCommandRequest(RequestStatus status, String command, String argument) implements Request {
    @Override
    public RequestType getType() {
        return RequestType.COMMAND;
    }
}
