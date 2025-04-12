package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.handler.ConnectionHandler;
import ru.se.ifmo.s466351.lab6.client.handler.ReadHandler;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Client {
    public static ByteBuffer buffer = ByteBuffer.allocate(8012);
    private static ReadHandler readHandler = new ReadHandler(buffer);

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        Config config = new Config(Paths.get("config.properties"));

        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
        System.out.println("Connected to " + socketChannel.getRemoteAddress());

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                if (key.isConnectable()) {
                    ConnectionHandler.handleConnect(key);
                } else if (key.isReadable()) {
                    readHandler.read(key);
                }
                keys.clear();
            }
        }
    }
}
