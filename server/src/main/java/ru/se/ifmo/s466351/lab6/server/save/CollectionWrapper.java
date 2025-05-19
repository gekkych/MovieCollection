package ru.se.ifmo.s466351.lab6.server.save;

import java.util.Collection;

public interface CollectionWrapper<T> {
    Collection<T> getCollection();
    boolean has(T element);
}
