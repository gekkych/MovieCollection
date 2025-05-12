package ru.se.ifmo.s466351.lab6.common.dto;


import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

public record CoordinateDTO(int x,
                            Double y) implements DTO {
    public CoordinateDTO {
        MovieValidator.validateY(y);
    }
}
