package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));

            socketChannel.connect(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
            System.out.println("Connected to " + socketChannel.getRemoteAddress());

            String message = "Hello World!";
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);

            ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
            int byteRead = socketChannel.read(responseBuffer);
            if (byteRead > 0) {
                responseBuffer.flip();
                byte[] bytes = new byte[responseBuffer.remaining()];
                responseBuffer.get(bytes);
                String response = new String(bytes);
                System.out.println(response);
            }
        } catch (IOException | ConfigException e) {
            System.out.println(e.getMessage());
        }
    }
}
