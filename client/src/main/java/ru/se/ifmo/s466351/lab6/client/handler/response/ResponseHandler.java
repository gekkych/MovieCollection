package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public interface ResponseHandler {
    Request handle(ServerResponse response);
}
