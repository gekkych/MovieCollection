package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class ShowCommand extends Command {
    MovieDeque movieDeque;

    public ShowCommand(MovieDeque movieDeque) {
        super("show");
        this.movieDeque = movieDeque;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        StringBuilder result = new StringBuilder();
        if (movieDeque.getCollection().isEmpty()) {
            return "Пустая коллекция";
        }
        for(Movie movie : movieDeque.getCollection()) {
            if (context.getRole().hasAccess(Role.ADMIN) || movie.getOwnerLogin().equals(context.getUser().getLogin())) {
                result.append(movie.toString()).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String description() {
        return this.getName() + " - список элементов коллекции";
    }
}
