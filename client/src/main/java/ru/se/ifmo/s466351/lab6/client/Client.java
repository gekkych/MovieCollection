package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.input.InputHandler;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Client {
    public static ByteBuffer buffer = ByteBuffer.allocate(8012);
    private static Scanner scanner = new Scanner(System.in);
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
                    handleConnect(key);
                } else if (key.isReadable()) {
                    handleRead(key);
                } else if (key.isWritable()) {
                    handleWrite(key);
                }
            }
            keys.clear();
        }
    }

    private static void handleConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.finishConnect()) {
            System.out.println("Подключено к серверу");
            channel.register(key.selector(), SelectionKey.OP_WRITE);
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        InputHandler inputHandler = new InputHandler(scanner);
        ClientCommandRequest request_ = inputHandler.inputCommandRequest();
        String request = JsonUtils.toJson(request_);
        buffer.put(request.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        int bytesRead = channel.read(buffer);
        if (bytesRead > 0) {
            buffer.flip();
            ServerResponse response = JsonUtils.fromJson(StandardCharsets.UTF_8.decode(buffer).toString(), ServerResponse.class);
            System.out.println("Ответ сервера: " + response.message());
            channel.register(key.selector(), SelectionKey.OP_WRITE);
            buffer.clear();
        }
    }
}
