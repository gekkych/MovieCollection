package ru.se.ifmo.s466351.lab6.client.handler.response;

import ru.se.ifmo.s466351.lab6.client.input.CommandRequestInput;
import ru.se.ifmo.s466351.lab6.common.request.Request;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;

public class NoAccessResponseHandler implements ResponseHandler {
    @Override
    public Request handle(ServerResponse response) {
        System.out.println(response.message());
        return CommandRequestInput.inputCommandRequest();
    }
}
