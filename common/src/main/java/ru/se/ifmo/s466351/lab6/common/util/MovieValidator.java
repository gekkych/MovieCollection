package ru.se.ifmo.s466351.lab6.common.util;


import ru.se.ifmo.s466351.lab6.common.dto.MovieGenre;
import ru.se.ifmo.s466351.lab6.common.exception.MovieFieldNotValidatedException;

public class MovieValidator {
    public static void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new MovieFieldNotValidatedException("title не должно быть null или пустой строкой");
        }
    }

    public static void validateY(Double y) {
        if(y == null || y > 102) {
            throw new MovieFieldNotValidatedException("y = " + (y != null ? y : "null") + " не должно быть null или превосходить 102");
        }
    }

    public static void validateOscarCount(int oscarCount) {
        if(oscarCount <= 0) {
            throw new MovieFieldNotValidatedException("oscarCount = " + oscarCount + " не должно быть меньше нуля");
        }
    }

    public static void validateGenre(MovieGenre genre) {
        if (genre == null) {
            throw new MovieFieldNotValidatedException("genre не должно быть null");
        }
    }

    public static void validateDirectorName(String directorName) {
        if (directorName == null || directorName.isEmpty()) {
            throw new MovieFieldNotValidatedException("directorName не должно быть null или пустой строкой");
        }
    }

    public static void validateDirectorHeight(int height) {
        if(height <= 0) {
            throw new MovieFieldNotValidatedException("height = " + height + " не должно быть меньше нуля");
        }
    }

    public static void validateDirectorWeight(int weight) {
        if(weight <= 0) {
            throw new MovieFieldNotValidatedException("weight = " + weight + " не должно быть меньше нуля");
        }
    }
}
