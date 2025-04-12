package ru.se.ifmo.s466351.lab6.server.exception;

public class MovieCannotBeAddedException extends MovieDequeException {
    public MovieCannotBeAddedException(String cause) {
        super("Фильм не может быть добавлен: " + cause);
    }
}
