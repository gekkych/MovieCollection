package ru.se.ifmo.s466351.lab6.server.exception;

public class CommandException extends RuntimeException {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public CommandException(String message) {
        super(ANSI_RED + "Ошибка при исполнении команды. " + message + ANSI_RESET);
    }
}
