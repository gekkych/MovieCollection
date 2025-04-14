package ru.se.ifmo.s466351.lab6.client.exception;

public class ClientException extends RuntimeException {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public ClientException(String message) {
        super(ANSI_RED + "Ошибка на клиенте. " + message + ANSI_RESET);
    }
}
