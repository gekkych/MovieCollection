package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Coordinates;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Person;
import ru.se.ifmo.s466351.lab6.server.exception.InvalidCommandArgumentException;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class UpdateCommand extends Command implements Receiver<MovieDTO> {
    private final MovieDeque movies;

    public UpdateCommand(MovieDeque movies) {
        super("update");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data, SelectionKey key) {
        AuthClientContext context = (AuthClientContext) key.attachment();
        try {
            long id = Long.parseLong(argument);
            Movie movie = movies.getById(id);
            if (movie == null) {
                return "ID не найден";
            }
            if (!context.getRole().hasAccess(Role.ADMIN) && !movie.getOwnerLogin().equals(context.getUser().getLogin())) {
                return "Нет доступа к фильму";
            }
            movie.setTitle(data.title());
            movie.setCoordinates(Coordinates.fromDTO(data.coordinates()));
            movie.setGenre(data.genre());
            movie.setMpaaRating(data.rating());
            movie.setOscarsCount(data.oscarCount());
            movie.setDirector(Person.fromDTO(data.director()));

            return "Фильм успешно обновлён";
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("неверный формат id");
        }
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
        return this.getName() + " - обновить значения элемента по ID";
    }
}
