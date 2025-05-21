package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GroupByGenreCommand extends Command {
    MovieDeque movies;

    public GroupByGenreCommand(MovieDeque movies) {
        super("group_by_genre");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        return movies.getCollection()
                .stream()
                .filter(movie -> context.getRole().hasAccess(Role.ADMIN) ||
                        movie.getOwnerLogin().equals(context.getUser().getLogin()))
                .filter(movie -> movie.getGenre() != null)
                .collect(Collectors.groupingBy(
                        Movie::getGenre,
                        Collectors.mapping(Movie::toString, Collectors.joining("\n"))
                ))
                .entrySet().stream()
                .map(entry -> switch (entry.getKey()) {
                    case ACTION -> "Боевики:\n" + entry.getValue();
                    case COMEDY -> "Комедии:\n" + entry.getValue();
                    case SCIENCE_FICTION -> "Научная фантастика:\n" + entry.getValue();
                })
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public String description() {
        return getName() + " - сгруппировать фильма по ID";
    }
}
