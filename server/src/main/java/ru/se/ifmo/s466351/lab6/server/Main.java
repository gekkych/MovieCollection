package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));
            serverChannel.bind(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
            System.out.println("Listening on " + serverChannel.getLocalAddress());

            SocketChannel clientChannel = serverChannel.accept();
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int bytesRead = clientChannel.read(buffer);

            if (bytesRead > 0) {
                buffer.flip();

                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);

                String message = new String(bytes);
                System.out.println(message);
            }
        } catch (IOException | ConfigException e) {
            System.out.println(e.getMessage());
        }
    }
}
