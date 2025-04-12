package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;

public class AverageOfOscarCountCommand extends Command {
    private final MovieDeque movies;

    public AverageOfOscarCountCommand(MovieDeque movies) {
        super("average_of_oscar_count");
        this.movies = movies;
    }

    @Override
    public String execute(String argument) {
        if (movies.getMovies().isEmpty()) {
            return "Коллекция фильмов пуста";
        }
        double oscarSum = 0;
        for (Movie movie : movies.getMovies()) {
            oscarSum += movie.getOscarsCount();
        }
        double average = oscarSum / movies.getMovies().size();
        return String.format("Среднее количество оскаров %.2f%n", average);
    }

    @Override
    public String description() {
        return this.getName() + " - среднее количество оскаров";
    }
}
