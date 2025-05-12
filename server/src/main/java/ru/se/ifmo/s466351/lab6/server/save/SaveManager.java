package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.exception.CommandException;
import ru.se.ifmo.s466351.lab6.server.exception.MovieDequeException;

import java.io.*;


public class SaveManager<W extends CollectionWrapper<?>> {
    private final FileManager fileManager;
    private final Serializer<W> serializer;

    public SaveManager(Serializer<W> serializer, String fileName) {
        this.serializer = serializer;
        this.fileManager = FileManager.getInstance(fileName);
    }

    public void save(W wrapper) {
        try {
            String data = serializer.serialize(wrapper);
            fileManager.save(data);
        } catch (IOException e) {
            throw new CommandException("Ошибка сохранения: " + e.getMessage());
        }
    }

    public W load() {
        try {
            String data = fileManager.load();
            return serializer.deserialize(data);
        } catch (IOException e) {
            throw new MovieDequeException("Ошибка загрузки: " + e.getMessage());
        }
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}