package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

public class AddCommand extends Command implements MovieDataReceiver {
    private final MovieDeque movies;

    public AddCommand(MovieDeque movies) {
        super("add");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, MovieDTO data) {
        movies.add(data);
        return "Фильм успешно добавлен в коллекцию.";
    }

    @Override
    public String execute(String argument) {
        return "Нужно использовать execute(String, MovieData)";
    }

    @Override
    public String description() {
        return this.getName() + " - добавить новый элемент в коллекцию";
    }
}
