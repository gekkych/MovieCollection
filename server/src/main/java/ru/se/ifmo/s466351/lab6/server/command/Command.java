package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public abstract class Command implements Comparable<Command> {
    private final String name;
    private Role accessLevel = Role.MEMBER;

    public Command(String name) {
        this.name = name;
    }

    public abstract String execute(String argument, SelectionKey key);

    public abstract String description();

    @Override
    public int compareTo(Command command) {
        return this.getName().compareTo(command.getName());
    }

    public String getName() {
        return name;
    }

    public Role getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Role accessLevel) {
        this.accessLevel = accessLevel;
    }
}