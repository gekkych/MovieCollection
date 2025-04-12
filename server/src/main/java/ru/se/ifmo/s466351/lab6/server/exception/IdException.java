package ru.se.ifmo.s466351.lab6.server.exception;

public class IdException extends MovieDequeException {
    public IdException(String message) {
        super("Ошибка с ID. " + message);
    }
}
