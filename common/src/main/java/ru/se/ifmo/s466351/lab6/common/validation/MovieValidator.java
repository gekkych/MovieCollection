package ru.se.ifmo.s466351.lab6.common.validation;


import ru.se.ifmo.s466351.lab6.common.dto.MovieGenre;
import ru.se.ifmo.s466351.lab6.common.exception.MovieFieldNotValidatedException;

public class MovieValidator {
    public static boolean validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new MovieFieldNotValidatedException("title не должно быть null или пустой строкой");
        }
        return true;
    }

    public static boolean validateY(Double y) {
        if(y == null || y > 102) {
            throw new MovieFieldNotValidatedException("y = " + (y != null ? y : "null") + " не должно быть null или превосходить 102");
        }
        return true;
    }

    public static boolean validateOscarCount(int oscarCount) {
        if(oscarCount <= 0) {
            throw new MovieFieldNotValidatedException("oscarCount = " + oscarCount + " не должно быть меньше нуля");
        }
        return true;
    }

    public static boolean validateGenre(MovieGenre genre) {
        if (genre == null) {
            throw new MovieFieldNotValidatedException("genre не должно быть null");
        }
        return true;
    }

    public static boolean validateDirectorName(String directorName) {
        if (directorName == null || directorName.isEmpty()) {
            throw new MovieFieldNotValidatedException("directorName не должно быть null или пустой строкой");
        }
        return true;
    }

    public static boolean validateDirectorHeight(int height) {
        if(height <= 0) {
            throw new MovieFieldNotValidatedException("height = " + height + " не должно быть меньше нуля");
        }
        return true;
    }

    public static boolean validateDirectorWeight(int weight) {
        if(weight <= 0) {
            throw new MovieFieldNotValidatedException("weight = " + weight + " не должно быть меньше нуля");
        }
        return true;
    }
}
