package ru.se.ifmo.s466351.lab6.server.user;

public enum Role {
    GUEST(1),
    MEMBER(2),
    ADMIN(3);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public boolean hasAccess(Role requiredRole) {
        return this.level >= requiredRole.level;
    }
}
