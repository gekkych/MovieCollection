package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.server.exception.UserCannotBeAdded;

import java.util.HashSet;

public class UserCollection {
    private final HashSet<AuthClientContext> users;

    public UserCollection() {
        this.users = new HashSet<>();
        users.add(new AuthClientContext(new User("admin", "0")));
    }

    public void add(AuthClientContext context) throws UserCannotBeAdded {
        if (context.getUser() == null || context.getUser().getLogin() == null || context.getUser().getHashedPassword() == null) {
            throw new UserCannotBeAdded("Неполная информация о пользователе.");
        }
        if (users.contains(context)) {
            throw new UserCannotBeAdded("Логин уже занят.");
        }
        users.add(context);
    }

    public HashSet<AuthClientContext> getUsers() {
        return users;
    }
}
