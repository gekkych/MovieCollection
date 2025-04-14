package ru.se.ifmo.s466351.lab6.client.exception;

public class ClientIsDisconnectedException extends CriticalClientException {
    public ClientIsDisconnectedException() {
        super("Клиент отключён от сервера.");
    }
}
