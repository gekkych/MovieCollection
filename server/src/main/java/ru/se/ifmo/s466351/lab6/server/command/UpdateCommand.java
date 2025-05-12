package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Coordinates;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Person;
import ru.se.ifmo.s466351.lab6.server.exception.InvalidCommandArgumentException;

public class UpdateCommand extends Command implements MovieDataReceiver {
    private final MovieDeque movies;

    public UpdateCommand(MovieDeque movies) {
        super("update");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data) {
        try {
            long id = Long.parseLong(argument);
            for (Movie movie : movies.getCollection()) {
                if (movie.getId() == id) {
                    movie.setTitle(data.title());
                    movie.setCoordinates(Coordinates.fromDTO(data.coordinates()));
                    movie.setGenre(data.genre());
                    movie.setMpaaRating(data.rating());
                    movie.setOscarsCount(data.oscarCount());
                    movie.setDirector(Person.fromDTO(data.director()));

                    return "Фильм успешно обновлён";
                }
            }
            return "ID не найден";
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("неверный формат id");
        }
    }

    @Override
    public String execute(String argument) {
        return "Нужно использовать execute(String, MovieData)";
    }

    @Override
    public String description() {
        return this.getName() + " - обновить значения элемента по ID";
    }
}
