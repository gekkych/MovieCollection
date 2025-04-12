package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
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

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            channel.close();
            return;
        }
        buffer.flip();
        ServerResponse responseJson = JsonUtils.fromJson(StandardCharsets.UTF_8.decode(buffer).toString(), ServerResponse.class);
        System.out.println(responseJson.message());
        buffer.clear();

        if (responseJson.status() == ResponseStatus.NEED_MOVIE_DATA) {
            WriteHandler.write(key, new ClientMovieDataRequest(ResponseStatus.OK, MovieFieldInput.inputMovieData()), buffer);
            buffer.clear();
            channel.register(key.selector(), SelectionKey.OP_READ);
            return;
        }
        WriteHandler.write(key, CommandRequestInput.inputCommandRequest(), buffer);
        buffer.clear();
        channel.register(key.selector(), SelectionKey.OP_READ);
    }
}
