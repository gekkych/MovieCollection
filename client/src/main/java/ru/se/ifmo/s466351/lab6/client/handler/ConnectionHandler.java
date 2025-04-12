package ru.se.ifmo.s466351.lab6.client.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionHandler {
        public static void handleConnect(SelectionKey key) throws IOException {
            SocketChannel channel = (SocketChannel) key.channel();
            if (channel.finishConnect()) {
                System.out.println("Подключено к серверу");
                channel.register(key.selector(), SelectionKey.OP_WRITE);
            }
        }
}
