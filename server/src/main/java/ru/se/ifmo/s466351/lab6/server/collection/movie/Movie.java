package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.dto.MovieGenre;
import ru.se.ifmo.s466351.lab6.common.dto.MpaaRating;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Movie implements Comparable<Movie> {
    private long id;
    private String title;
    private Coordinates coordinates;
    private Date creationDate;
    private int oscarsCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;

    private Movie() {
    }

    private Movie(long id, String title, int x, Double y, MovieGenre genre, MpaaRating mpaaRating, int oscarsCount, Person director) {
        setId(id);
        setTitle(title);
        setCoordinates(new Coordinates(x, y));
        setCreationDate(new Date());
        setGenre(genre);
        setMpaaRating(mpaaRating);
        setOscarsCount(oscarsCount);
        setDirector(director);
        creationDate = new Date();
    }

    public static class MovieBuilder {
        private long id;
        private final String title;
        private final Coordinates coordinates;
        private final int oscarsCount;
        private final MovieGenre genre;
        private MpaaRating mpaaRating;
        private Person director;

        public MovieBuilder(String title, Coordinates coordinates, MovieGenre genre, int oscarsCount) {
            this.title = title;
            this.coordinates = coordinates;
            this.genre = genre;
            this.oscarsCount = oscarsCount;
        }

        public MovieBuilder setMpaaRating(MpaaRating mpaaRating) {
            this.mpaaRating = mpaaRating;
            return this;
        }

        public MovieBuilder setDirector(Person director) {
            this.director = director;
            return this;
        }

        public MovieBuilder setDirector(String name, LocalDate birthday, int height, int weight) {
            this.director = new Person(name, birthday, height, weight);
            return this;
        }

        public MovieBuilder setDirector(String name, int height, int weight) {
            this.director = new Person(name, height, weight);
            return this;
        }

        public MovieBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public Movie build() {
            return new Movie(this.id, this.title, this.coordinates.getX(), this.coordinates.getY(), this.genre, this.mpaaRating, this.oscarsCount, this.director);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        MovieValidator.validateTitle(title);
        this.title = title;

    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(int oscarsCount) {
        MovieValidator.validateOscarCount(oscarsCount);
        this.oscarsCount = oscarsCount;

    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        MovieValidator.validateGenre(genre);
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return oscarsCount == movie.oscarsCount && title.equals(movie.title) && genre.equals(movie.genre) && director.equals(movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oscarsCount, title, genre, director);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(id).append(")").append(" ").append(title).append("   Жанр ").append(getGenre()).append("   Количество Оскаров ").append(oscarsCount);
        if (mpaaRating != null) {
            builder.append("    Возрастное ограничение ").append(mpaaRating);
        }
        if (director != null) {
            builder.append("    Режиссёр ").append(director.getName());
            if (director.getBirthday() != null) {
                builder.append(" ").append(director.getBirthday());
            }
        }
        builder.append("    ").append(creationDate);
        return builder.toString();
    }

    @Override
    public int compareTo(Movie other) {
        return Integer.compare(this.getOscarsCount(), other.getOscarsCount());
    }

    public MovieDTO toDTO() {
        return new MovieDTO(getTitle(), getCoordinates().toDTO(), getGenre(), getMpaaRating(), getOscarsCount(), getDirector().toDTO());
    }

    public static Movie fromDTO(MovieDTO dto) {
        return new Movie.MovieBuilder(dto.title(), Coordinates.fromDTO(dto.coordinates()), dto.genre(), dto.oscarCount()).setDirector(Person.fromDTO(dto.director())).setMpaaRating(dto.rating()).build();
    }
}