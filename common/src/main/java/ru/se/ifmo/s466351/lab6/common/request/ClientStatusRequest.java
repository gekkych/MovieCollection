package ru.se.ifmo.s466351.lab6.common.request;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("status")
public record ClientStatusRequest(RequestStatus status) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
