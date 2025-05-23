package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;
import java.util.Iterator;

public class RemoveIfLowerCommand extends Command implements Receiver<MovieDTO> {
    private final MovieDeque movies;

    public RemoveIfLowerCommand(MovieDeque movies) {
        super("remove_if_lower");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data, SelectionKey key) {
        StringBuilder result = new StringBuilder();
        AuthClientContext context = (AuthClientContext) key.attachment();

        Iterator<Movie> iterator = movies.getCollection().iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (data.oscarCount() > movie.getOscarsCount()) {
                if (context.getRole().hasAccess(Role.ADMIN) || movie.getOwnerLogin().equals(context.getUser().getLogin())) {
                    result.append("Удалён фильм ").append(movie.getTitle()).append(" с айди ").append(movie.getId());
                    iterator.remove();
                }
            }
        }
        if (result.isEmpty()) {
            return "Фильмов меньше данного не найдено";
        }
        return result.toString();
    }

    @Override
    public Class<MovieDTO> getType() {
        return MovieDTO.class;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        return "Нужно использовать execute(String, MovieData)";
    }

    @Override
    public String description() {
        return this.getName() + " - удаляет все элементы коллекции, которые меньше заданного";
    }
}
