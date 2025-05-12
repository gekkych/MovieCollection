package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;

import java.nio.channels.SelectionKey;

public class AverageOfOscarCountCommand extends Command {
    private final MovieDeque movies;

    public AverageOfOscarCountCommand(MovieDeque movies) {
        super("average_of_oscar_count");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        if (movies.getCollection().isEmpty()) {
            return "Коллекция фильмов пуста";
        }
        double average = movies.getCollection().stream()
                .mapToInt(Movie::getOscarsCount)
                .average()
                .orElse(0);
        return String.format("Среднее количество оскаров %.2f%n", average);
    }

    @Override
    public String description() {
        return this.getName() + " - среднее количество оскаров";
    }
}
