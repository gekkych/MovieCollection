package ru.se.ifmo.s466351.lab6.server.collection;


import ru.se.ifmo.s466351.lab6.server.collection.movie.Movie;
import ru.se.ifmo.s466351.lab6.server.exception.IdException;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class IdGenerator {
    private final Set<Long> idSet = new HashSet<>();
    private long nextFreeID;

    public IdGenerator() {
        nextFreeID = 1;
    }

    public long generateID() {
        while (idSet.contains(nextFreeID)) {
            nextFreeID++;
        }
        idSet.add(nextFreeID);
        return nextFreeID;
    }

    public void resetId() {
        nextFreeID = 1;
        idSet.clear();
    }

    public void releaseId(long id) {
        if(!idSet.remove(id)) {
            throw new IdException("ID не найден.");
        }
        nextFreeID = Math.min(nextFreeID, id);
    }

    public void validateId(ArrayDeque<Movie> movies) {
        int counter = 0;
        for (Movie movie : movies) {
            idSet.add(movie.getId());
            counter++;

            if (idSet.size() != counter) {
                throw new IdException("Коллизия ID.");
            }
        }
    }
}
