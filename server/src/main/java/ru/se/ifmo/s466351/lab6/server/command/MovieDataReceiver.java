package ru.se.ifmo.s466351.lab6.server.command;


import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;

public interface MovieDataReceiver {
    String execute(String argument, MovieDTO data);
}