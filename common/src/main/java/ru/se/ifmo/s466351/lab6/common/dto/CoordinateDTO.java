package ru.se.ifmo.s466351.lab6.common.dto;


import ru.se.ifmo.s466351.lab6.common.validation.MovieValidator;

public record CoordinateDTO(int x, Double y) {
    public CoordinateDTO {
        MovieValidator.validateY(y);
    }
}
