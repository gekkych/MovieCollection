package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientStatusRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionHandler {
    public static void handleConnect(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            channel.finishConnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
