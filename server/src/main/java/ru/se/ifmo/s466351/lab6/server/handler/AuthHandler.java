package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientAuthenticationRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class AuthHandler {
    UserCollection users;
    ActiveConnection activeConnection;

    public AuthHandler(UserCollection users, ActiveConnection activeConnection) {
        this.users = users;
        this.activeConnection = activeConnection;
    }

    public ServerResponse handle(ClientAuthenticationRequest request, SelectionKey key) throws IOException {

        for (AuthClientContext authClientContext : users.getUsers()) {
            if (request.login().equals(authClientContext.getUser().getLogin())) {
                if (EncryptionUtils.sha1Hash(request.password() + "serverSalt").equals(authClientContext.getUser().getHashedPassword())) {
                    activeConnection.connect(authClientContext, key);
                    return new ServerResponse(ResponseStatus.OK, "успешная авторизация");
                }
            }
        }
        return new ServerResponse(ResponseStatus.NOT_AUTHENTICATED, "неверный логин или пароль");
    }
}
