package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ReadHandler {
    public static ServerResponse read(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            channel.close();
            return null;
        }

        buffer.flip();
        ServerResponse responseJson = JsonUtils.fromJson(StandardCharsets.UTF_8.decode(buffer).toString(), ServerResponse.class);
        buffer.clear();
        return responseJson;
    }
}
