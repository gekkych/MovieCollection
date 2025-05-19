package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.exception.CommandException;
import ru.se.ifmo.s466351.lab6.server.exception.MovieDequeException;

import java.io.*;


public class SaveManager<W extends CollectionWrapper<?>> {
    private final Storage storage;
    private final Serializer<W> serializer;

    public SaveManager(Serializer<W> serializer, Storage storage) {
        this.serializer = serializer;
        this.storage = storage;
    }

    public void save(W wrapper) throws MovieDequeException {
        String data = serializer.serialize(wrapper);
        storage.save(data);
    }

    public W load() {

        String data = storage.load();
        return serializer.deserialize(data);

    }

    public Storage getStorage() {
        return storage;
    }
}