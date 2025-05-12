package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.exception.CommandException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileManager {
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

    public void save(String data) throws IOException {
        Files.writeString(filePath, data);
    }

    public String load() throws IOException {
        return Files.readString(filePath);
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
