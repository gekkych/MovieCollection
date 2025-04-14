package ru.se.ifmo.s466351.lab6.common.request;


public record ClientErrorStatusRequest(RequestStatus status) implements Request {
    @Override
    public RequestStatus getStatus() {
        return status;
    }
}
