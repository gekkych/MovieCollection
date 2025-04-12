package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class WriteHandler {
    public static void write(SelectionKey key, Request request, ByteBuffer buffer) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String request_ = JsonUtils.toJson(request);
        buffer.put(request_.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        channel.register(key.selector(), SelectionKey.OP_READ);
    }
}
