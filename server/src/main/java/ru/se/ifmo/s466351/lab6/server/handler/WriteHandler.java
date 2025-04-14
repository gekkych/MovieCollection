package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class WriteHandler {
    public static void send(SelectionKey key, ServerResponse response, ByteBuffer buffer) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String json = JsonUtils.toJson(response);
        buffer.clear();
        buffer.put(json.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
    }
}
