package ru.se.ifmo.s466351.lab6.client.exception;

public class ResponseRouterException extends ClientException {
    public ResponseRouterException(String message) {
        super("Ответ сервера не может быть обработан: " + message);
    }
}
