package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

import java.nio.channels.SelectionKey;

public class InfoCommand extends Command{
    MovieDeque movies;

    public InfoCommand(MovieDeque movies) {
        super("info");
        this.movies = movies;
    }

    @Override
    public String execute(String arguments, SelectionKey key) {
        return movies.toString();
    }

    @Override
    public String description() {
        return this.getName() + " - информация о коллекции";
    }
}
