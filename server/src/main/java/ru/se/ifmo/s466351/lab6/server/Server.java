package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.exception.MovieDequeException;
import ru.se.ifmo.s466351.lab6.server.handler.*;
import ru.se.ifmo.s466351.lab6.server.save.FileManager;
import ru.se.ifmo.s466351.lab6.server.save.MovieDequeXmlSerializer;
import ru.se.ifmo.s466351.lab6.server.save.SaveManager;
import ru.se.ifmo.s466351.lab6.server.save.UserCollectionXmlSerializer;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.ClientContext;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private static final SaveManager<MovieDeque> movieSaveManager = new SaveManager<>(new MovieDequeXmlSerializer(), FileManager.getInstance("movie"));
    private static final SaveManager<UserCollection> userSaveManager = new SaveManager<>(new UserCollectionXmlSerializer(), FileManager.getInstance("user"));
    private static UserCollection userCollection;
    private static final ActiveConnection connection = new ActiveConnection();


    public static void main(String[] args) throws IOException {
        MovieDeque movies;
        try {
            movies = movieSaveManager.load();
            userCollection = userSaveManager.load();
        } catch (MovieDequeException e) {
            System.out.println(e.getMessage());
            return;
        }

        CommandManager commandManager = new CommandManager(movies, movieSaveManager, connection, userCollection);
        commandManager.initialize();
        RequestRouter requestRouter = new RequestRouter(commandManager, new UserCollection());
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Config config = new Config(Paths.get("config.properties"));
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open(); Selector selector = Selector.open()) {

            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(config.getHost(), config.getPort()));

            System.out.println("Сервер ждёт подключений");
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(50);
                if (consoleReader.ready()) {
                    String input = consoleReader.readLine().trim();
                    if ("save".equalsIgnoreCase(input)) {
                        System.out.println(commandManager.getCommand("save").execute(null, null));
                    } else if ("exit".equalsIgnoreCase(input)) {
                        shutdown(commandManager, selector, serverChannel);
                        return;
                    }
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    System.out.println(selectedKeys);
                    try {
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isAcceptable()) {
                            ConnectionHandler.acceptClient(serverChannel, selector);
                        } else if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            ClientContext context = (ClientContext) key.attachment();
                            ByteBuffer buffer = context.getReadBuffer();

                            Request request = ReadHandler.read(key, buffer);
                            ServerResponse response = requestRouter.route(request, key);

                            if (request != null) {
                                context = (ClientContext) key.attachment();
                                context.setCurrentRequest(request);
                                context.setLastResponse(response);

                                if (key.isValid()) {
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                            } else {
                                key.cancel();
                                clientChannel.close();
                            }
                        } else if (key.isWritable()) {
                            ClientContext context = (ClientContext) key.attachment();
                            WriteHandler.send(key, context.getLastResponse(), context.getWriteBuffer());
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    } catch (IOException e) {
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException ex) {

                        }
                    }
                }
            }
        }
    }

    private static void shutdown(CommandManager commandManager, Selector selector, ServerSocketChannel serverChannel) {
        try {
            System.out.println(commandManager.getCommand("save").execute(null, null));
            userSaveManager.save(userCollection);

            for (SelectionKey key : selector.keys()) {
                try {
                    key.channel().close();
                } catch (IOException e) {
                    System.out.println("Ошибка при закрытии канала: " + e.getMessage());
                }
            }

            selector.close();
            serverChannel.close();

            System.out.println("Все ресурсы закрыты. Сервер завершил работу.");
        } catch (IOException e) {
            System.out.println("Ошибка при завершении сервера: " + e.getMessage());
        }
    }
}
