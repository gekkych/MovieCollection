package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ReadHandler {
    private final ByteBuffer buffer;

    public ReadHandler(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Request read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            channel.close();
            return null;
        }
        buffer.flip();

        String requestJson = StandardCharsets.UTF_8.decode(buffer).toString();
        buffer.clear();
        return JsonUtils.fromJson(requestJson, Request.class);
    }
}
