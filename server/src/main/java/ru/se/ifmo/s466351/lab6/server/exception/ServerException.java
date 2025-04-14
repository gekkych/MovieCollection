package ru.se.ifmo.s466351.lab6.server.exception;

public class ServerException extends RuntimeException {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public ServerException(String description) {
        super(ANSI_RED + "Ошибка в MovieDeque. " + description + ANSI_RESET);
    }
}
