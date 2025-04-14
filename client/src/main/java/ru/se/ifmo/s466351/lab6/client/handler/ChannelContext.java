package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientStatusRequest;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;

import java.nio.ByteBuffer;

public class ChannelContext {
    public final ByteBuffer readBuffer = ByteBuffer.allocate(4096);
    public final ByteBuffer writeBuffer = ByteBuffer.allocate(4096);
    public Request currentRequest = new ClientStatusRequest(RequestStatus.PING);
}
