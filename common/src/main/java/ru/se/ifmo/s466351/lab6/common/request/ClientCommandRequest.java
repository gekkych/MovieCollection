package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("command")
public record ClientCommandRequest(RequestStatus status, String command, String argument) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
