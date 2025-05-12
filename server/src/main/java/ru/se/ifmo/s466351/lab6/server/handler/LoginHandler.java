package ru.se.ifmo.s466351.lab6.server.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientAuthenticationRequest;
import ru.se.ifmo.s466351.lab6.common.request.ClientRegistrationRequest;
import ru.se.ifmo.s466351.lab6.common.response.ResponseStatus;
import ru.se.ifmo.s466351.lab6.common.response.ServerResponse;
import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;
import ru.se.ifmo.s466351.lab6.server.Server;
import ru.se.ifmo.s466351.lab6.server.exception.UserCannotBeAdded;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.AuthClientContext;
import ru.se.ifmo.s466351.lab6.server.user.User;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class LoginHandler {
    UserCollection users;
    ActiveConnection activeConnection;

    public LoginHandler(UserCollection users, ActiveConnection activeConnection) {
        this.users = users;
        this.activeConnection = activeConnection;
    }

    public ServerResponse authHandle(ClientAuthenticationRequest request, SelectionKey key) {

        for (User user : users.getCollection()) {
            if (request.login().equals(user.getLogin())) {
                if (EncryptionUtils.sha1Hash(request.password() + user.getUserSalt()).equals(user.getHashedPassword())) {
                    activeConnection.connect(new AuthClientContext(user), key);
                    return new ServerResponse(ResponseStatus.OK, "успешная авторизация");
                }
            }
        }
        return new ServerResponse(ResponseStatus.NOT_AUTHENTICATED, "неверный логин или пароль");
    }

    public ServerResponse regHandle(ClientRegistrationRequest request, SelectionKey key) throws IOException {
        try {
            AuthClientContext authClientContext = new AuthClientContext(new User(request.login(), request.password()));
            users.add(authClientContext.getUser());
            activeConnection.connect(authClientContext, key);
            return new ServerResponse(ResponseStatus.OK, "Пользователь успешно зарегистрирован");
        } catch (UserCannotBeAdded e) {
            return new ServerResponse(ResponseStatus.NOT_AUTHENTICATED, e.getMessage());
        }
    }
}
