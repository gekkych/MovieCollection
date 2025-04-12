package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.dto.CoordinateDTO;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

public class Coordinates {
    private int x;
    private Double y;

    private Coordinates() {
    }

    public Coordinates(int x, Double y) {
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(Double y) {
        MovieValidator.validateY(y);
        this.y = y;
    }

    public Double getY() {
        return y;
    }

    public CoordinateDTO toDTO() {
        return new CoordinateDTO(getX(), getY());
    }

    public static Coordinates fromDTO(CoordinateDTO dto) {
        return new Coordinates(dto.x(), dto.y());
    }
}

