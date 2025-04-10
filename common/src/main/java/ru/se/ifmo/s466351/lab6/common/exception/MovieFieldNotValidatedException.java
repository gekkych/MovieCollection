package ru.se.ifmo.s466351.lab6.common.exception;

public class MovieFieldNotValidatedException extends RuntimeException {
    public MovieFieldNotValidatedException(String cause) {
        super("Значение не валидировано: " + cause);
    }
}
