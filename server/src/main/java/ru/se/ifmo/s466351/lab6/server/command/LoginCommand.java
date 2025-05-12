package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.UserDTO;
import ru.se.ifmo.s466351.lab6.common.util.EncryptionUtils;
import ru.se.ifmo.s466351.lab6.server.user.*;

import java.nio.channels.SelectionKey;

public class LoginCommand extends Command implements Receiver<UserDTO> {
    private final ActiveConnection connection;
    private final UserCollection users;

    public LoginCommand(ActiveConnection connection, UserCollection users) {
        super("login");
        setAccessLevel(Role.GUEST);
        this.connection = connection;
        this.users = users;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        return "Нужно использовать execute(String, User)";
    }

    @Override
    public String execute(String argument, UserDTO user, SelectionKey key) {
        ClientContext context = (ClientContext) key.attachment();
        if (context.isAuthenticated()) {
            return "Пользователь уже авторизован";
        }

        for (User other : users.getCollection()) {
            if (user.login().equals(other.getLogin())) {
                if (EncryptionUtils.sha1Hash(user.password() + other.getUserSalt()).equals(other.getHashedPassword())) {
                    connection.connect(new AuthClientContext(new User(user.login(), user.password())), key);
                    return "успешная авторизация";
                }
            }
        }
        return "пользователь не найден";
    }

    @Override
    public Class<UserDTO> getType() {
        return UserDTO.class;
    }

    @Override
    public String description() {
        return this.getName() + " - вход по логину и паролю.";
    }
}
