package ru.se.ifmo.s466351.lab6.server.exception;

public class MovieFieldNotValidatedException extends MovieDequeException {
    /**
     * Ошибка при не успешной валидации переменной
     * @param cause описание ошибки
     */
    public MovieFieldNotValidatedException(String cause) {
        super("Значение не валидировано: " + cause);
    }
}
