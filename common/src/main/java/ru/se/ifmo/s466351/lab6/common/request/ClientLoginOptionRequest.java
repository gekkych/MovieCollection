package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("loginOption")
public record ClientLoginOptionRequest(RequestStatus status, LoginOption option) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
