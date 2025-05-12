package ru.se.ifmo.s466351.lab6.common.dto;

public record UserDTO(String login,
                      String password) implements DTO{
}
