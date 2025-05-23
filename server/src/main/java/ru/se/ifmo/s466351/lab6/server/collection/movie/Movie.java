package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.common.dto.MovieGenre;
import ru.se.ifmo.s466351.lab6.common.dto.MpaaRating;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"id", "title", "genre", "mpaaRating", "director", "oscarsCount", "coordinates", "ownerLogin"})
public class Movie implements Comparable<Movie> {
    private long id;
    private String title;
    private Coordinates coordinates;
    private int oscarsCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;
    private String ownerLogin;

    private Movie() {
    }

    private Movie(long id, String title, int x, Double y, MovieGenre genre, MpaaRating mpaaRating, int oscarsCount, Person director, String ownerLogin) {
        setId(id);
        setTitle(title);
        setCoordinates(new Coordinates(x, y));
        setGenre(genre);
        setMpaaRating(mpaaRating);
        setOscarsCount(oscarsCount);
        setDirector(director);
        setOwnerLogin(ownerLogin);
    }

    public static class MovieBuilder {
        private long id;
        private final String title;
        private final Coordinates coordinates;
        private final int oscarsCount;
        private final MovieGenre genre;
        private MpaaRating mpaaRating;
        private Person director;
        private String ownerLogin;

        public MovieBuilder(long id, String title, Coordinates coordinates, MovieGenre genre, int oscarsCount) {
            this.id = id;
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

        public MovieBuilder setOwnerLogin(String login) {
            this.ownerLogin = login;
            return this;
        }

        public Movie build() {
            return new Movie(this.id, this.title, this.coordinates.getX(), this.coordinates.getY(), this.genre, this.mpaaRating, this.oscarsCount, this.director, this.ownerLogin);
        }
    }

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        MovieValidator.validateTitle(title);
        this.title = title;

    }

    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    @XmlElement
    public int getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(int oscarsCount) {
        MovieValidator.validateOscarCount(oscarsCount);
        this.oscarsCount = oscarsCount;

    }

    @XmlElement
    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        MovieValidator.validateGenre(genre);
        this.genre = genre;
    }

    @XmlElement(nillable = true)
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    @XmlElement(nillable = true)
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
        builder.append("    ").append(ownerLogin);
        return builder.toString();
    }

    @Override
    public int compareTo(Movie other) {
        return Integer.compare(this.getOscarsCount(), other.getOscarsCount());
    }
}