package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;

import java.util.Iterator;

public class RemoveIfLowerCommand extends Command implements Confirmable, MovieDataReceiver {
    private final MovieDeque movies;

    public RemoveIfLowerCommand(MovieDeque movies) {
        super("remove_if_lower");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data) {
        StringBuilder result = new StringBuilder();

        Iterator<Movie> iterator = movies.getMovies().iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (data.oscarCount() > movie.getOscarsCount()) {
                result.append("Удалён фильм ").append(movie.getTitle()).append(" с айди ").append(movie.getId());
                iterator.remove();
            }
        }
        if (result.isEmpty()) {
            return "Фильмов меньше данного не найдено";
        }
        return result.toString();
    }

    @Override
    public String execute(String argument) {
        return "Нужно использовать execute(String, MovieData)";
    }

    @Override
    public String description() {
        return this.getName() + " - удаляет все элементы коллекции, которые меньше заданного";
    }
}
