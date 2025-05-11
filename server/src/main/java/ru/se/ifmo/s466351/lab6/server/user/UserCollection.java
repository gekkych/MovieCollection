package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;
import ru.se.ifmo.s466351.lab6.server.exception.UserCannotBeAdded;

import java.util.ArrayList;

public class UserCollection {
    private final ArrayList<AuthClientContext> users;

    public UserCollection() {
        this.users = new ArrayList<>();
        users.add(new AuthClientContext(new User("admin", "0")));
    }

    public void add(AuthClientContext context) {
        if (context.getUser() == null || context.getUser().getLogin() == null || context.getUser().getHashedPassword() == null) {
            throw new UserCannotBeAdded("Неполная информация о пользователе.");
        }
        users.add(context);
    }

    public ArrayList<AuthClientContext> getUsers() {
        return users;
    }
}
