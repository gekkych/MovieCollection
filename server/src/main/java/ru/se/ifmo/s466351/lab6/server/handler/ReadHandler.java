package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestType;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ReadHandler {
    private final ByteBuffer buffer;
    private final CommandHandler commandHandler;

    public ReadHandler(ByteBuffer buffer, CommandHandler commandHandler) {
        this.buffer = buffer;
        this.commandHandler = commandHandler;
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            channel.close();
            return;
        }

        buffer.flip();
        String requestJson = StandardCharsets.UTF_8.decode(buffer).toString();
        buffer.clear();
        Request request = JsonUtils.fromJson(requestJson, Request.class);
        if (request.getType() == RequestType.COMMAND) {
            commandHandler.handle((ClientCommandRequest) request);
        } else if (request.getType() == RequestType.MOVIE) {
            System.out.println("ТЕСТ");
        }
    }
}
