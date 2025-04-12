package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.exception.InvalidCommandArgumentException;

public class RemoveByIdCommand extends Command implements Confirmable {
    private final MovieDeque movies;

    public RemoveByIdCommand(MovieDeque movies) {
        super("remove_by_id");
        this.movies = movies;
    }

    @Override
    public String execute(String argument) {
        try {
            long id = Long.parseLong(argument);
            movies.removeById(id);
            return "Фильм с id " + id + " удалён.";
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("неверный формат id");
        }
    }

    @Override
    public String description() {
        return this.getName() + " - удалить элемент по ID";
    }
}
