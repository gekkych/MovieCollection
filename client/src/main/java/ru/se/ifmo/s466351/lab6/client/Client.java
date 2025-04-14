package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.exception.CriticalClientException;
import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.client.handler.ConnectionHandler;
import ru.se.ifmo.s466351.lab6.client.handler.ReadHandler;
import ru.se.ifmo.s466351.lab6.client.handler.ResponseRouter;
import ru.se.ifmo.s466351.lab6.client.handler.WriteHandler;
import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.common.request.ClientStatusRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
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
        Request currentRequest = new ClientStatusRequest(RequestStatus.ERROR);

        try (SocketChannel socketChannel = SocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(config.getHost(), config.getPort()));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            System.out.println("Подключение к серверу");

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();

                for (SelectionKey key : keys) {
                    try {
                        if (key.isConnectable()) {
                            ConnectionHandler.handleConnect(key);
                            currentRequest = new ClientStatusRequest(RequestStatus.PING);
                            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
                        } else if (key.isReadable()) {
                            lastResponse = readHandler.read(key);
                            if (lastResponse == null) {
                                System.out.println("Сервер закрыл соединение.");
                                close();
                                return;
                            }
                            currentRequest = ResponseRouter.route(lastResponse);
                            socketChannel.register(selector, SelectionKey.OP_WRITE);

                        } else if (key.isWritable()) {
                            WriteHandler.write(key, currentRequest, buffer);
                            socketChannel.register(key.selector(), SelectionKey.OP_READ);
                        }

                    } catch (ResponseRouterException e) {
                        System.out.println(e.getMessage());
                        currentRequest = CommandRequestInput.inputCommandRequest();
                        socketChannel.register(key.selector(), SelectionKey.OP_WRITE);

                    } catch (IOException e) {
                        System.out.println("Соединение с сервером потеряно. Завершение работы клиента.");
                        socketChannel.close();
                        close();
                        return;

                    } catch (Exception e) {
                        System.out.println("Непредвиденная ошибка: " + e.getMessage());
                        socketChannel.close();
                        close();
                        return;
                    }
                }
                keys.clear();
            }

        } catch (IOException | CriticalClientException e) {
            System.out.println("Ошибка при запуске клиента: " + e.getMessage());
            close();
        }
    }

    public static void close() {
        buffer.clear();
    }
}
