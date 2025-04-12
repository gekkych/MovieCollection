package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.request.ClientMovieDataRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.JsonUtils;
import ru.se.ifmo.s466351.lab6.server.command.Command;
import ru.se.ifmo.s466351.lab6.server.command.MovieDataReceiver;

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

        try {
            if (buffer.remaining() == 0) {
                System.err.println("Пустой буфер");
                return;
            }

            String requestJson = StandardCharsets.UTF_8.decode(buffer).toString();
            buffer.clear();

            Request request = JsonUtils.fromJson(requestJson, Request.class);

            if (request instanceof ClientMovieDataRequest movieRequest) {
                WriteHandler.send(channel, new ServerResponse(ResponseStatus.OK, commandHandler.handle(movieRequest.movieData(), channel)), buffer);
            } else {
                ClientCommandRequest commandRequest = (ClientCommandRequest) request;
                Command command = commandHandler.getCommandManager().getCommand(commandRequest.command());

                if (command instanceof MovieDataReceiver) {
                    PendingRequest.add(channel, commandRequest);
                    WriteHandler.send(channel, new ServerResponse(ResponseStatus.NEED_MOVIE_DATA, ""), buffer);
                } else {
                    String result = commandHandler.handle(commandRequest, channel);
                    WriteHandler.send(channel, new ServerResponse(ResponseStatus.OK, result), buffer);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки запроса: " + e.getMessage());
            buffer.clear();
            key.cancel();
            try {
                channel.close();
            } catch (IOException ex) {
                System.err.println("Ошибка при закрытии канала: " + ex.getMessage());
            }
        }
    }
}
