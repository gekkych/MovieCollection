package ru.se.ifmo.s466351.lab6.common.request;


public record ClientStatusRequest(RequestStatus status) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
