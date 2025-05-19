package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.util.ClientSaltGenerator;
import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"login", "hashedPassword", "userSalt"})
public class User {
    private String login;
    private String hashedPassword;
    private String userSalt;

    private User() {}

    public User(String login, String password) {
        this.login = login;
        this.userSalt = ClientSaltGenerator.generateSalt();
        this.hashedPassword = EncryptionUtils.sha1Hash(password + userSalt);
    }

    @XmlElement(name = "login")
    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    @XmlElement(name = "password")
    public String getHashedPassword() {
        return hashedPassword;
    }

    private void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @XmlElement(name = "salt")
    public String getUserSalt() {
        return userSalt;
    }

    private void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
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
