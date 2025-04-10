package ru.se.ifmo.s466351.lab6.common.dto;

import ru.se.ifmo.s466351.lab6.common.validation.MovieValidator;

import java.time.LocalDate;

public record PersonDTO(String name, LocalDate birthday, int directorHeight, int directorWeight) {
    public PersonDTO {
        MovieValidator.validateDirectorName(name);
        MovieValidator.validateDirectorHeight(directorHeight);
        MovieValidator.validateDirectorWeight(directorWeight);
    }
}
