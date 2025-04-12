package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;

public class SumOfOscarCountCommand extends Command{
    private final MovieDeque movies;

    public SumOfOscarCountCommand(MovieDeque movies) {
        super("sum_of_oscar_count");
        this.movies = movies;
    }

    @Override
    public String execute(String argument) {
        int oscarSum = 0;
        for (Movie movie : movies.getMovies()) {
            oscarSum += movie.getOscarsCount();
        }
        return "Сумма всех оскаров " + oscarSum;
    }

    @Override
    public String description() {
        return this.getName() + " - сумма всех оскаров";
    }
}
