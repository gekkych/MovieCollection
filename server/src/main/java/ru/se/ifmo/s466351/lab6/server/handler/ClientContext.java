package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

import java.nio.ByteBuffer;

public class ClientContext {
    public final ByteBuffer readBuffer = ByteBuffer.allocate(4096);
    public final ByteBuffer writeBuffer = ByteBuffer.allocate(4096);
    public Request currentRequest;
    public ServerResponse lastResponse;
}
