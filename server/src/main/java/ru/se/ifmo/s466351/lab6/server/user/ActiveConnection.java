package ru.se.ifmo.s466351.lab6.server.user;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class ActiveConnection {
    private final HashMap<AuthClientContext, SelectionKey> connections;

    public ActiveConnection() {
        connections = new HashMap<>();
    }

    public void connect(AuthClientContext authClientContext, SelectionKey key) {
        key.attach(authClientContext);
        connections.put(authClientContext, key);
    }
}
