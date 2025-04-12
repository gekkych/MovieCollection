package ru.se.ifmo.s466351.lab6.server.command;

public abstract class Command implements Comparable<Command> {
    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract String execute(String argument);

    public abstract String description();

    @Override
    public int compareTo(Command command) {
        return this.getName().compareTo(command.getName());
    }

    public String getName() {
        return name;
    }
}