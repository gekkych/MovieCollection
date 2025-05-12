package ru.se.ifmo.s466351.lab6.server.save;

public interface Serializer<W extends CollectionWrapper<?>> {
    String serialize(W collectionWrapper);

    W deserialize(String string);
}
