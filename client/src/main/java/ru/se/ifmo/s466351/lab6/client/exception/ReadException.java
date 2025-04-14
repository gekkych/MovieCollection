package ru.se.ifmo.s466351.lab6.client.exception;

public class ReadException extends ClientException {
    public ReadException(String message) {
        super("Ошибка при чтении данных с сервера: " + message);
    }
}
