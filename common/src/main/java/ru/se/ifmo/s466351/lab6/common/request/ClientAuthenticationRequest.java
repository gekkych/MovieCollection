package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("auth")
public record ClientAuthenticationRequest(RequestStatus status, String login, String password) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
