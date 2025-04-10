package ru.se.ifmo.s466351.lab6.client.request;

import ru.se.ifmo.s466351.lab6.common.util.Request;

public class ClientRequestFactory {
    public Request create(String commandName, String commandArgument) {
        return new CommandRequest(commandName, commandArgument);
    } //С новыми реквестами будет перегрузка
}
