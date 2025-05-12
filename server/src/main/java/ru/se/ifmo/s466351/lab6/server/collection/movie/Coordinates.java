package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.dto.CoordinateDTO;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"x", "y"})
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

    @XmlElement
    public int getX() {
        return x;
    }

    public void setY(Double y) {
        MovieValidator.validateY(y);
        this.y = y;
    }
    @XmlElement
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

