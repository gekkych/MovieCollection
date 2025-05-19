package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.exception.CommandException;
import ru.se.ifmo.s466351.lab6.server.exception.MovieDequeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileManager implements Storage {
    private final Path filePath;
    private static final HashMap<String, FileManager> instances = new HashMap<>();

    private FileManager(String path) {
        this.filePath = Paths.get(path);
        create();
    }

    public static FileManager getInstance(String path) {
        if (!instances.containsKey(path)) {
            instances.put(path, new FileManager(path));
        }
        return instances.get(path);
    }

    public void save(String data) {
        try {
            Files.writeString(filePath, data);
        } catch (IOException e) {
            throw new CommandException("Ошибка сохранения: " + e.getMessage());
        }
    }

    public String load() {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new MovieDequeException("Ошибка загрузки: " + e.getMessage());
        }
    }

    public boolean exists() {
        return Files.exists(filePath);
    }

    public void create() {
        try {
            if (!exists()) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new CommandException("");
        }
    }

    public String getFilePath() {
        return filePath.normalize().toString();
    }
}
