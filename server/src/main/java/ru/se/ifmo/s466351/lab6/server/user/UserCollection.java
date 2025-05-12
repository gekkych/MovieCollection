package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.server.save.CollectionWrapper;
import ru.se.ifmo.s466351.lab6.server.exception.UserCannotBeAdded;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

@XmlRootElement
public class UserCollection implements CollectionWrapper<User> {
    private HashSet<User> users;

    public UserCollection() {
        this.users = new HashSet<>();
    }

    public void add(User user) throws UserCannotBeAdded {
        if (user == null || user.getLogin() == null || user.getHashedPassword() == null) {
            throw new UserCannotBeAdded("Неполная информация о пользователе.");
        }
        if (users.contains(user)) {
            throw new UserCannotBeAdded("Логин уже занят.");
        }
        users.add(user);
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    @Override
    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public HashSet<User> getCollection() {
        return users;
    }
}
