package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class PendingRequest {
    private static final HashMap<SocketChannel, ClientCommandRequest> pendingCommands = new HashMap<>();

    public static void add(SocketChannel socketChannel, ClientCommandRequest clientCommandRequest) {
        pendingCommands.remove(socketChannel);
        pendingCommands.put(socketChannel, clientCommandRequest);
    }

    public static void remove(SocketChannel socketChannel) {
        pendingCommands.remove(socketChannel);
    }

    public static ClientCommandRequest get(SocketChannel socketChannel) {
        return pendingCommands.get(socketChannel);
    }
}
