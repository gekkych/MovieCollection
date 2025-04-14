package ru.se.ifmo.s466351.lab6.server.handler;

import java.io.IOException;
import java.nio.channels.*;

public class ConnectionHandler {
    public static void acceptClient(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel == null) return;
        clientChannel.configureBlocking(false);
        System.out.println("Подключён клиент: " + clientChannel.getRemoteAddress());
    }
}