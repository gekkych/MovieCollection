package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

import java.nio.channels.SelectionKey;

public class ClearCommand extends Command {
    private final MovieDeque movies;

    public ClearCommand(MovieDeque movies) {
        super("clear");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        movies.clear();
        return "Коллекция очищена";
    }

    @Override
    public String description() {
        return getName() + " - удаляет все элементы коллекции";
    }

}
