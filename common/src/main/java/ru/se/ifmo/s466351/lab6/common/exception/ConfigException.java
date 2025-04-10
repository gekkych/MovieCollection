package ru.se.ifmo.s466351.lab6.common.exception;

public class ConfigException extends RuntimeException {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public ConfigException(String message) {
        super(ANSI_RED + "Ошибка с конфигом. " + message + ANSI_RESET);
    }
}
