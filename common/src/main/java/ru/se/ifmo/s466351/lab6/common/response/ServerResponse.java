package ru.se.ifmo.s466351.lab6.common.response;

public record ServerResponse(ResponseStatus status, String message) implements Response {}
