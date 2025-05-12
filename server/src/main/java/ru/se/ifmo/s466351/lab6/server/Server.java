package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.handler.ConnectionHandler;
import ru.se.ifmo.s466351.lab6.server.handler.ReadHandler;
import ru.se.ifmo.s466351.lab6.server.handler.RequestRouter;
import ru.se.ifmo.s466351.lab6.server.handler.WriteHandler;
import ru.se.ifmo.s466351.lab6.server.save.MovieDequeXmlSerializer;
import ru.se.ifmo.s466351.lab6.server.save.SaveManager;
import ru.se.ifmo.s466351.lab6.server.save.UserCollectionXmlSerializer;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class Server {
    private final ExecutorService requestReaderPool = Executors.newFixedThreadPool(4);
    private final ForkJoinPool requestProcessingPool = new ForkJoinPool();
    private final ExecutorService responseSenderPool = Executors.newCachedThreadPool();

    private final MovieDeque movieDeque = new MovieDeque();
    private final UserCollection userCollection = new UserCollection();
    private final SaveManager<MovieDeque> movieSaveManager = new SaveManager<>(new MovieDequeXmlSerializer(), "movie");
    private final SaveManager<UserCollection> userSaveManager = new SaveManager<>(new UserCollectionXmlSerializer(), "user");
    private final ActiveConnection activeConnection = new ActiveConnection();
    private final CommandManager commandManager = new CommandManager(movieDeque, movieSaveManager, userSaveManager, activeConnection, userCollection);
    private final RequestRouter requestRouter = new RequestRouter(commandManager, userCollection);

    public static void main(String[] args) throws IOException {
        Config config = new Config(Paths.get("config"));
        new Server().start(config.getPort());
    }

    public void start(int port) {
        commandManager.initialize();

        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер запущен на порту: " + port);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    try {
                        if (key.isAcceptable()) {
                            acceptConnection(serverSocket, selector);
                        } else if (key.isReadable()) {
                            requestReaderPool.submit(() -> readRequest(key));
                        }
                    } catch (Exception e) {
                        System.err.println("Ошибка при обработке ключа: " + e.getMessage());
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException ignored) {}
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    private void acceptConnection(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        ConnectionHandler.acceptClient(serverSocket, selector);
        serverSocket.register(selector, SelectionKey.OP_READ);
    }

    private void readRequest(SelectionKey key) {
        try {
            ClientContext context = (ClientContext) key.attachment();
            Request request = ReadHandler.read(key, context.getReadBuffer());
            if (request != null) {
                processRequestAsync(request, key, context);
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }
    }

    private void processRequestAsync(Request request, SelectionKey key, ClientContext context) {
        requestProcessingPool.submit(() -> {
            try {
                ServerResponse response = requestRouter.route(request, key);
                sendResponseAsync(response, key, context);
            } catch (Exception e) {
                System.err.println("Ошибка обработки: " + e.getMessage());
            }
        });
    }

    private void sendResponseAsync(ServerResponse response, SelectionKey key, ClientContext context) {
        responseSenderPool.submit(() -> {
            try {
                WriteHandler.send(key, response, context.getWriteBuffer());
                key.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                System.err.println("Ошибка отправки: " + e.getMessage());
            }
        });
    }
}