package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.exception.ServerException;
import ru.se.ifmo.s466351.lab6.server.handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static final ByteBuffer buffer = ByteBuffer.allocate(8012);
    private static final SaveManager saveManager = new SaveManager("save");

    public static void main(String[] args) throws IOException {
        MovieDeque movies = saveManager.loadFromXML();
        CommandManager commandManager = new CommandManager(movies, saveManager);
        commandManager.initialize();
        RequestRouter requestRouter = new RequestRouter(commandManager);
        ReadHandler readHandler = new ReadHandler(buffer);
        Config config = new Config(Paths.get("config.properties"));
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open(); Selector selector = Selector.open()) {

            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(config.getHost(), config.getPort()));

            System.out.println("Регистрирую канал на OP_CONNECT");
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                Request lastRequest;
                ServerResponse currentResponse = new ServerResponse(ResponseStatus.ERROR, "Неожиданная ошибка");
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ConnectionHandler.acceptClient(serverChannel, selector);
                        serverChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        lastRequest = readHandler.read(key);
                        currentResponse = requestRouter.route(lastRequest, key);
                        serverChannel.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        WriteHandler.send(key, currentResponse, buffer);
                        serverChannel.register(selector, SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException | ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
