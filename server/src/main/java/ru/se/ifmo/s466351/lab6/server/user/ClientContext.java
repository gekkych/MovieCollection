package ru.se.ifmo.s466351.lab6.server.user;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

import java.nio.ByteBuffer;

public abstract class ClientContext {
    private final ByteBuffer readBuffer;
    private final ByteBuffer writeBuffer;
    private Request currentRequest;
    private ServerResponse lastResponse;
    private boolean isAuthenticated = false;

    public ClientContext() {
        readBuffer = ByteBuffer.allocate(4096);
        writeBuffer = ByteBuffer.allocate(4096);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setLastResponse(ServerResponse lastResponse) {
        this.lastResponse = lastResponse;
    }

    public ServerResponse getLastResponse() {
        return lastResponse;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
