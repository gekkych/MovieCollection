package ru.se.ifmo.s466351.lab6.server.exception;

import ru.se.ifmo.s466351.lab6.server.user.User;

public class UserCannotBeAdded extends UserException {
    public UserCannotBeAdded(String message) {
        super(message);
    }
}
