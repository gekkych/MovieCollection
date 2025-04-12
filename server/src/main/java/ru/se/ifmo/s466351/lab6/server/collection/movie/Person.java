package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.dto.PersonDTO;
import ru.se.ifmo.s466351.lab6.common.validation.MovieValidator;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private String name;
    private LocalDate birthday;
    private int height;
    private int weight;

    public Person(String name, LocalDate birthday, int height, int weight) {
        setName(name);
        setBirthday(birthday);
        setWeight(weight);
        setHeight(height);
    }

    public Person(String name, int height, int weight) {
        setName(name);
        setWeight(weight);
        setHeight(height);
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        if (MovieValidator.validateDirectorName(name)) {
            this.name = name;
        }
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setWeight(int weight) {
        if (MovieValidator.validateDirectorWeight(weight)) {
            this.weight = weight;
        }
    }

    public void setHeight(int height) {
        if (MovieValidator.validateDirectorHeight(height)) {
            this.height = height;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return name.equals(person.name) &&
                height == person.height &&
                weight == person.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, weight);
    }

    public PersonDTO toDTO() {
        return new PersonDTO(getName(), getBirthday(), getHeight(), getWeight());
    }

    public static Person fromDTO(PersonDTO dto) {
        return new Person(dto.name(), dto.birthday(), dto.directorHeight(), dto.directorWeight());
    }
}
