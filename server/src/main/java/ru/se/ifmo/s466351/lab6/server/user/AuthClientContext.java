package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.util.ClientSaltGenerator;


public class AuthClientContext extends ClientContext {
    private final User user;

    public AuthClientContext(User user) {
        super();
        this.user = user;
        setAuthenticated(true);
    }

    public User getUser() {
        return user;
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
