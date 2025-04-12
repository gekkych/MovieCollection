package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.input.InputHandler;
import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open(); Scanner scanner = new Scanner(System.in)) {
            InputHandler inputHandler = new InputHandler(scanner);
            Config config = new Config(Paths.get("config.properties"));

            socketChannel.connect(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
            System.out.println("Connected to " + socketChannel.getRemoteAddress());

            while (true) {
                ClientCommandRequest request = inputHandler.inputCommandRequest();
                ByteBuffer buffer = ByteBuffer.wrap(JsonUtils.toJson(request).getBytes());
                socketChannel.write(buffer);

                ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
                int byteRead = socketChannel.read(responseBuffer);
                if (byteRead > 0) {
                    responseBuffer.flip();
                    String jsonResponse = StandardCharsets.UTF_8.decode(responseBuffer).toString();
                    ServerResponse response = JsonUtils.fromJson(jsonResponse, ServerResponse.class);
                    System.out.println("Ответ сервера: " + response.message());
                }
            }
        } catch (IOException | ConfigException e) {
            System.out.println(e.getMessage());
        }


    }
}
