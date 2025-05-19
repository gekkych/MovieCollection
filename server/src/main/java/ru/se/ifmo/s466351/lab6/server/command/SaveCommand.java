package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.save.FileManager;
import ru.se.ifmo.s466351.lab6.server.save.SaveManager;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class SaveCommand extends Command {
    private final MovieDeque movies;
    private final SaveManager<MovieDeque> saveManager;

    public SaveCommand(MovieDeque movies, SaveManager<MovieDeque> saveManager) {
        super("save");
        setAccessLevel(Role.ADMIN);
        this.movies = movies;
        this.saveManager = saveManager;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        saveManager.save(movies);
        return "Коллекция сохранена";
    }

    @Override
    public String description() {
        return this.getName() + " (ТРЕБУЕТСЯ СЕРВЕРНЫЙ ДОСТУП) - сохранить коллекцию в файл";
    }
}

