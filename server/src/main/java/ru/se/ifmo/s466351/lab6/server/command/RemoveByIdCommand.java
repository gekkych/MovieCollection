package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.exception.InvalidCommandArgumentException;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class RemoveByIdCommand extends Command {
    private final MovieDeque movies;

    public RemoveByIdCommand(MovieDeque movies) {
        super("remove_by_id");
        this.movies = movies;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        try {
            AuthClientContext context = (AuthClientContext) key.attachment();
            long id = Long.parseLong(argument);
            if (context.getRole().hasAccess(Role.ADMIN) || movies.getById(id).getOwnerLogin().equals(context.getUser().getLogin())) {
                movies.removeById(id);
                return "Фильм с id " + id + " удалён.";
            } else {
                return "Нет права доступа";
            }
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("неверный формат id");
        }
    }

    @Override
    public String description() {
        return this.getName() + " - удалить элемент по ID";
    }
}
