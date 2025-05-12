package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.client.exception.ResponseRouterException;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class ErrorResponseHandler implements ResponseHandler {
    @Override
    public Request handle(ServerResponse response) {
        throw new ResponseRouterException(response.message());
    }
}
