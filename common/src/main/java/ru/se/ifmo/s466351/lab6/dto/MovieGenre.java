package ru.se.ifmo.s466351.lab6.dto;

public enum MovieGenre {
    ACTION("Action"),
    COMEDY("Comedy"),
    SCIENCE_FICTION("Science fiction");

    private final String value;

    MovieGenre(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
