package ru.se.ifmo.s466351.lab6.server.save;

public interface Storage {
    void save(String data);
    String load();
}
