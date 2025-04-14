package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.SaveManager;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

public class SaveCommand extends Command {
    private final MovieDeque movies;
    private final SaveManager saveManager;

    public SaveCommand(MovieDeque movies, SaveManager saveManager) {
        super("save");
        this.movies = movies;
        this.saveManager = saveManager;
    }

    @Override
    public String execute(String argument) {
        saveManager.saveInXML(movies);
        return "Коллекция сохранена в файл " + saveManager.getFileName();
    }

    @Override
    public String description() {
        return this.getName() + " (ТРЕБУЕТСЯ СЕРВЕРНЫЙ ДОСТУП) - сохранить коллекцию в файл";
    }
}

