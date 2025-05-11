package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.util.ClientSaltGenerator;
import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;

import java.util.Objects;

public class User {
    private final String login;
    private final String hashedPassword;
    private final String userSalt;

    public User(String login, String password) {
        this.login = login;
        this.userSalt = ClientSaltGenerator.generateSalt();
        this.hashedPassword = EncryptionUtils.sha1Hash(password + userSalt);
    }

    public String getUserSalt() {
        return userSalt;
    }

    public String getLogin() {
        return login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(login, other.login);
    }
}
