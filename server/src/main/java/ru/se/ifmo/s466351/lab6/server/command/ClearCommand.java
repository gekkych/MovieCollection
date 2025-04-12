package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

public class ClearCommand extends Command implements Confirmable {
    private final MovieDeque movies;

    public ClearCommand(MovieDeque movies) {
        super("clear");
        this.movies = movies;
    }

    @Override
    public String execute(String argument) {
        movies.clear();
        return "Коллекция очищена";
    }

    @Override
    public String description() {
        return getName() + " - удаляет все элементы коллекции";
    }

}
