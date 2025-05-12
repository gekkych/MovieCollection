package ru.se.ifmo.s466351.lab6.server.collection;


import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.save.CollectionWrapper;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Coordinates;
import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.exception.IdException;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

@XmlRootElement
public class MovieDeque implements CollectionWrapper<Movie> {
    private final IdGenerator idGenerator;
    private ArrayDeque<Movie> movies = new ArrayDeque<>();
    private LocalDate creationDate;

    public MovieDeque() {
        idGenerator = new IdGenerator();
        creationDate = LocalDate.now();
    }

    public void add(MovieDTO data) {
        Movie.MovieBuilder movieBuilder = new Movie.MovieBuilder(idGenerator.generateID(), data.title(), new Coordinates(data.coordinates().x(), data.coordinates().y()), data.genre(), data.oscarCount());
        if (data.rating() != null) {
            movieBuilder.setMpaaRating(data.rating());
        }
        if (data.director() != null) {
            if (data.director().birthday() != null) {
                movieBuilder.setDirector(data.director().name(), data.director().birthday(), data.director().directorHeight(), data.director().directorWeight());
            } else {
                movieBuilder.setDirector(data.director().name(), data.director().directorHeight(), data.director().directorWeight());
            }
        }
        movies.add(movieBuilder.build());
        sortMovieDeque();
    }

    public void manageDeque() {
        idGenerator.validateId(movies);
        sortMovieDeque();
    }

    public void removeById(long id) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (movie.getId() == id) {
                iterator.remove();
                idGenerator.releaseId(id);
                System.out.println("Фильм успешно удалён из коллекции");
                return;
            }
        }
        throw new IdException("ID не найден.");
    }

    public void clear() {
        movies.clear();
        idGenerator.resetId();
    }

    public void sortMovieDeque() {
        ArrayList<Movie> movieList = new ArrayList<>(movies);
        movieList.sort(Comparator.comparingLong(Movie::getId));
        movies.clear();
        movies.addAll(movieList);
    }

    private void setMovies(ArrayDeque<Movie> movies) {
        this.movies = movies;
    }

    @Override
    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    public ArrayDeque<Movie> getCollection() {
        return movies;
    }

    private void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Тип ArrayDeque" + "\n" + "Дата создания " + creationDate + "\n" + "Количество элементов " + getCollection().size() + "\n";
    }
}
