package ru.se.ifmo.s466351.lab6.server.exception;

public class InvalidCommandArgumentException extends CommandException {
    public InvalidCommandArgumentException(String cause) {
        super("Неверное значение аргумента команды: " + cause);
    }
}
