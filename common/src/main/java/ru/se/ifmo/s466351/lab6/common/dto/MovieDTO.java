package ru.se.ifmo.s466351.lab6.common.dto;


import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

public record MovieDTO(String title,
                       CoordinateDTO coordinates,
                       MovieGenre genre,
                       MpaaRating rating,
                       int oscarCount,
                       PersonDTO director) implements DTO {
    public MovieDTO {
        MovieValidator.validateTitle(title);
        MovieValidator.validateGenre(genre);
        MovieValidator.validateOscarCount(oscarCount);
    }
}