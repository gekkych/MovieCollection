package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class ClearCommand extends Command {
    private final MovieDeque movies;

    public ClearCommand(MovieDeque movies) {
        super("clear");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        movies.getCollection()
                .removeIf(movie -> getAccessLevel().hasAccess(Role.ADMIN) ||
                movie.getOwnerLogin().equals(context.getUser().getLogin()));
        return "Коллекция очищена";
    }

    @Override
    public String description() {
        return getName() + " - удаляет все элементы коллекции";
    }

}
