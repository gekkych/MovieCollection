package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.exception.ClientException;
import ru.se.ifmo.s466351.lab6.client.exception.CriticalClientException;
import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.client.handler.ConnectionHandler;
import ru.se.ifmo.s466351.lab6.client.handler.ReadHandler;
import ru.se.ifmo.s466351.lab6.client.handler.ResponseRouter;
import ru.se.ifmo.s466351.lab6.client.handler.WriteHandler;
import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.common.request.ClientErrorStatusRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.Set;

public class Client {
    public static ByteBuffer buffer = ByteBuffer.allocate(8012);
    private static final ReadHandler readHandler = new ReadHandler(buffer);

    public static void main(String[] args) {
        ServerResponse lastResponse = null;
        Request currentRequest = new ClientErrorStatusRequest(RequestStatus.ERROR);
        try (SocketChannel socketChannel = SocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));

            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(config.getHost(), config.getPort()));
            System.out.println("Connected to " + socketChannel.getRemoteAddress());

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    try {
                        if (key.isConnectable()) {
                            ConnectionHandler.handleConnect(key);
                            currentRequest = CommandRequestInput.inputCommandRequest();
                            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
                        } else if (key.isReadable()) {
                            lastResponse = readHandler.read(key);
                            currentRequest = ResponseRouter.route(lastResponse);
                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            WriteHandler.write(key, currentRequest, buffer);
                            socketChannel.register(key.selector(), SelectionKey.OP_READ);
                        }
                    } catch (IOException | ResponseRouterException e) {
                        System.out.println(e.getMessage());
                        currentRequest = CommandRequestInput.inputCommandRequest();
                        socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
                    }
                    keys.clear();
                }
            }
        } catch (IOException | CriticalClientException e) {
            close();
            System.out.println(e.getMessage());
        }
    }

    public static void close() {
        buffer.clear();
    }
}
