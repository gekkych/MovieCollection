package ru.se.ifmo.s466351.lab6.server;

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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
public class Server {
    private static final ByteBuffer buffer = ByteBuffer.allocate(8012);
    private static SaveManager saveManager = new SaveManager("save");
    private static CommandManager commandManager = new CommandManager(new MovieDeque(), saveManager);
    public static void main(String[] args) throws IOException {
        commandManager.initialize();
        Config config = new Config(Paths.get("config.properties"));
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open(); Selector selector = Selector.open()) {

            serverChannel.bind(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        acceptClient(serverChannel, selector);
                    } else if (key.isReadable()) {
                        readAndResponse(key);
                    }
                }
            }
        }
    }

    private static void acceptClient(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Подключён клиент: " + clientChannel.getRemoteAddress());
    }

    private static void readAndResponse(SelectionKey key) throws IOException {
        String responseMessage;
        SocketChannel clientChannel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            clientChannel.close();
            return;
        }

        buffer.flip();
        ClientCommandRequest request = JsonUtils.fromJson(StandardCharsets.UTF_8.decode(buffer).toString(), ClientCommandRequest.class);

        if (commandManager.containsCommand(request.command())) {
            responseMessage = "Команда найдена";
        } else {
            responseMessage = "Команда не найдена";
        }

        ServerResponse responseStr = new ServerResponse(ResponseStatus.OK, responseMessage);
        String response = JsonUtils.toJson(responseStr);
        buffer.clear();
        buffer.put(response.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        clientChannel.write(buffer);
    }

}
