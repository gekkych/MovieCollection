package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientStatusRequest;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionHandler {
    public static ClientStatusRequest handleConnect(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            if (channel.finishConnect()) {
                return new ClientStatusRequest(RequestStatus.PING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
