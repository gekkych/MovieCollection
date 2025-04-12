package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;

public class ShowCommand extends Command {
    MovieDeque movieDeque;

    public ShowCommand(MovieDeque movieDeque) {
        super("show");
        this.movieDeque = movieDeque;
    }

    @Override
    public String execute(String argument) {
        StringBuilder result = new StringBuilder();
        if (movieDeque.getMovies().isEmpty()) {
            return "Пустая коллекция";
        }
        for(Movie movie : movieDeque.getMovies()) {
            result.append(movie.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String description() {
        return this.getName() + " - список элементов коллекции";
    }
}
