package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;
import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        SaveManager saveManager = new SaveManager("save");
        CommandManager commandManager = new CommandManager(new MovieDeque(), saveManager);
        commandManager.initialize();
        ServerResponse response;
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));
            serverChannel.bind(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
            System.out.println("Listening on " + serverChannel.getLocalAddress());

            SocketChannel clientChannel = serverChannel.accept();
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());

            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(8192);

                int bytesRead = clientChannel.read(buffer);

                if (bytesRead > 0) {
                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    String message = new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8);
                    ClientCommandRequest request = JsonUtils.fromJson(message, ClientCommandRequest.class);
                    if (commandManager.containsCommand(request.command())) {
                        response = new ServerResponse(ResponseStatus.OK, "команда найдена");
                    } else {
                        response = new ServerResponse(ResponseStatus.ERROR, "команда не найдена");
                    }
                    String jsonResponse = JsonUtils.toJson(response);
                    ByteBuffer jsonBuffer = ByteBuffer.wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
                    clientChannel.write(jsonBuffer);
                    System.out.println(message);
                }
            }
        } catch (IOException | ConfigException e) {
            System.out.println(e.getMessage());
        }
    }
}
