package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

public class GroupByGenreCommand extends Command {
    MovieDeque movies;

    public GroupByGenreCommand(MovieDeque movies) {
        super("group_by_genre");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        ArrayList<String> actionFilms = new ArrayList<>();
        ArrayList<String> comedyFilms = new ArrayList<>();
        ArrayList<String> scifiFilms = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        movies.getCollection().stream().filter(m -> m.getGenre() != null).forEach(movie -> {
            switch (movie.getGenre()) {
                case ACTION -> actionFilms.add(movie.toString());
                case COMEDY -> comedyFilms.add(movie.toString());
                case SCIENCE_FICTION -> scifiFilms.add(movie.toString());
            }
        });

        if (!actionFilms.isEmpty()) {
            result.append("Боевики: ").append("\n");
            for (String s : actionFilms) {
                result.append(s).append("\n");
            }
        }
        if (!comedyFilms.isEmpty()) {
            result.append("Комедии: ").append("\n");
            for (String s : comedyFilms) {
                result.append(s).append("\n");
            }
        }
        if (!scifiFilms.isEmpty()) {
            result.append("Научная фантастика: ").append("\n");
            for (String s : scifiFilms) {
                result.append(s).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String description() {
        return getName() + " - сгруппировать фильма по ID";
    }
}
