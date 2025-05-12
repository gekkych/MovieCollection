package ru.se.ifmo.s466351.lab6.server.collection.movie;

import ru.se.ifmo.s466351.lab6.common.adapter.LocalDateAdapter;
import ru.se.ifmo.s466351.lab6.common.dto.PersonDTO;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"name", "birthday", "height", "weight"})
public class Person {
    private String name;
    private LocalDate birthday;
    private int height;
    private int weight;

    public Person() {

    }

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

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement(nillable = true, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    @XmlElement
    public int getHeight() {
        return height;
    }

    @XmlElement
    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        MovieValidator.validateDirectorName(name);
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setWeight(int weight) {
        MovieValidator.validateDirectorWeight(weight);
        this.weight = weight;
    }

    public void setHeight(int height) {
        MovieValidator.validateDirectorHeight(height);
        this.height = height;

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
        return name.equals(person.name) && height == person.height && weight == person.weight;
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
