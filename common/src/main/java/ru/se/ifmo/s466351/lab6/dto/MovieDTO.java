package ru.se.ifmo.s466351.lab6.dto;


import ru.se.ifmo.s466351.lab6.validation.MovieValidator;

public record MovieDTO(String title, CoordinateDTO coordinates, MovieGenre genre, MpaaRating rating, int oscarCount, PersonDTO director) {
    public MovieDTO {
        MovieValidator.validateTitle(title);
        MovieValidator.validateGenre(genre);
        MovieValidator.validateOscarCount(oscarCount);
    }
}