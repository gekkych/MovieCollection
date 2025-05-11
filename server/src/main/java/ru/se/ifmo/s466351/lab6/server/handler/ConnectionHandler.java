package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
import ru.se.ifmo.s466351.lab6.server.user.PreAuthClientContext;

import java.io.IOException;
import java.nio.channels.*;

public class ConnectionHandler {
    public static ClientContext acceptClient(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel == null) return null;
        clientChannel.configureBlocking(false);
        System.out.println("Подключён клиент: " + clientChannel.getRemoteAddress());

        ClientContext context = new PreAuthClientContext();
        clientChannel.register(selector, SelectionKey.OP_READ).attach(context);
        return context;
    }
}