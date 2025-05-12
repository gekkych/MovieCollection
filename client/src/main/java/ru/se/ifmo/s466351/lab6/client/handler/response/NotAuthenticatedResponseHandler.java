package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.client.input.AuthRequestInput;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class NotAuthenticatedResponseHandler implements ResponseHandler {

    @Override
    public Request handle(ServerResponse response) {
        System.out.println(response.message());
        return AuthRequestInput.inputAuthRequest();
    }
}
