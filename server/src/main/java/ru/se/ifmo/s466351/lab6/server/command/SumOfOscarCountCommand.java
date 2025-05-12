package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class SumOfOscarCountCommand extends Command{
    private final MovieDeque movies;

    public SumOfOscarCountCommand(MovieDeque movies) {
        super("sum_of_oscar_count");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        int oscarSum = movies.getCollection().stream()
                .filter(movie -> context.getRole().hasAccess(Role.ADMIN) ||
                        movie.getOwnerLogin().equals(context.getUser().getLogin()))
                .mapToInt(Movie::getOscarsCount)
                .sum();
        return "Сумма всех оскаров " + oscarSum;
    }

    @Override
    public String description() {
        return this.getName() + " - сумма всех оскаров";
    }
}
