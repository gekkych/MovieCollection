package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.util.ClientSaltGenerator;


public class AuthClientContext extends ClientContext {
    private User user;
    private final String userSalt;

    public AuthClientContext(User user) {
        super();
        this.user = user;
        this.userSalt = ClientSaltGenerator.generateSalt();
        setAuthenticated(true);
    }

    public User getUser() {
        return user;
    }

    public String getUserSalt() {
        return userSalt;
    }
    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        if (this == obj) return true;
        AuthClientContext other = (AuthClientContext) obj;
        return user.equals(other.user);
    }
}
