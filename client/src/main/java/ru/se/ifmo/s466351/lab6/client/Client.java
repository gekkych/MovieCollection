package ru.se.ifmo.s466351.lab6.client;

import ru.se.ifmo.s466351.lab6.client.exception.CriticalClientException;
import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.client.handler.*;
import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        ServerResponse lastResponse = null;

        try (SocketChannel socketChannel = SocketChannel.open()) {
            Config config = new Config(Paths.get("config.properties"));
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(config.getHost(), config.getPort()));

            Selector selector = Selector.open();
            SelectionKey mKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
            mKey.attach(new ChannelContext());
            System.out.println("Подключение к серверу");

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();

                for (SelectionKey key : keys) {
                    if (!key.isValid()) {
                        continue;
                    }
                    ChannelContext context = (ChannelContext) key.attachment();
                    try {
                        if (key.isConnectable()) {
                            ConnectionHandler.handleConnect(key);
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (key.isReadable()) {
                            lastResponse = ReadHandler.read(key, context.readBuffer);
                            if (lastResponse == null) {
                                System.out.println("Сервер закрыл соединение.");
                                return;
                            }
                            context.currentRequest = ResponseRouter.route(lastResponse);
                            key.interestOps(SelectionKey.OP_WRITE);

                        } else if (key.isWritable()) {
                            WriteHandler.write(key, context.currentRequest, context.writeBuffer);
                            key.interestOps(SelectionKey.OP_READ);
                        }

                    } catch (ResponseRouterException e) {
                        System.out.println(e.getMessage());
                        context.currentRequest = CommandRequestInput.inputCommandRequest();
                        key.interestOps(SelectionKey.OP_WRITE);

                    } catch (IOException e) {
                        System.out.println("Соединение с сервером потеряно. Завершение работы клиента.");
                        socketChannel.close();
                        return;

                    } catch (Exception e) {
                        System.out.println("Непредвиденная ошибка: " + e.getMessage());
                        socketChannel.close();
                        return;
                    }
                }
                keys.clear();
            }

        } catch (IOException | CriticalClientException e) {
            System.out.println("Ошибка при запуске клиента: " + e.getMessage());
        }
    }
}
