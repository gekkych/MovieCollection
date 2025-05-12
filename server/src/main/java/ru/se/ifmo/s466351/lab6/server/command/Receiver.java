package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.common.dto.DTO;

import java.nio.channels.SelectionKey;

public interface Receiver<T extends DTO> {
    String execute(String argument, T object, SelectionKey key);
    Class<T> getType();
}