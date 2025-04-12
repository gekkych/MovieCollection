package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.common.util.Config;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.command.CommandManager;
import ru.se.ifmo.s466351.lab6.server.handler.CommandHandler;
import ru.se.ifmo.s466351.lab6.server.handler.ConnectionHandler;
import ru.se.ifmo.s466351.lab6.server.handler.ReadHandler;

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
    private static SaveManager saveManager = new SaveManager("save");
    private static CommandHandler commandHandler = new CommandHandler(new CommandManager(new MovieDeque(), saveManager));
    private static ReadHandler readHandler = new ReadHandler(buffer, commandHandler);

    public static void main(String[] args) throws IOException {
        commandHandler.getCommandManager().initialize();
        Config config = new Config(Paths.get("config.properties"));
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open(); Selector selector = Selector.open()) {

            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(config.getConfigHost(), config.getConfigPort()));

            System.out.println("Регистрирую канал на OP_CONNECT");
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ConnectionHandler.acceptClient(serverChannel, selector);
                    } else if (key.isReadable()) {
                        readHandler.read(key);
                    }
                }
            }
        }
    }
}
