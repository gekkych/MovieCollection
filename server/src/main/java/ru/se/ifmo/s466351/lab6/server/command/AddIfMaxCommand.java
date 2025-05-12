package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.exception.MovieCannotBeAddedException;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;

import java.nio.channels.SelectionKey;

public class AddIfMaxCommand extends Command implements Receiver<MovieDTO> {
    private final MovieDeque movies;
    public AddIfMaxCommand(MovieDeque movies) {
        super("add_if_max");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        for(Movie movie : movies.getCollection()) {
            if (data.oscarCount() <= movie.getOscarsCount()) {
                throw new MovieCannotBeAddedException("значение oscarCount не максимально.");
            }
        }
        movies.add(data, context.getUser().getLogin());
        return "Фильм успешно добавлен в коллекцию.";
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
        return this.getName() + " - добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
