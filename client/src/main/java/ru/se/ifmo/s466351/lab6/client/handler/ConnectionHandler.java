package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

public class ConnectionHandler {
    Config config = new Config(Paths.get("config.properties"));
    private final int MAX_RECONNECT_ATTEMPTS = config.getMAX_RECONNECT_ATTEMPTS();
    private final int RECONNECT_ATTEMPT_DELAY = config.getRECONNECT_ATTEMPT_DELAY();
    public static void handleConnect(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            if (channel.finishConnect()) {
                System.out.println("Подключено к серверу");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
