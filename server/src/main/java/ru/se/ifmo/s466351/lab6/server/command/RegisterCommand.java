package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.UserDTO;
import ru.se.ifmo.s466351.lab6.server.exception.UserCannotBeAdded;
import ru.se.ifmo.s466351.lab6.server.user.*;

import java.nio.channels.SelectionKey;

public class RegisterCommand extends Command implements Receiver<UserDTO> {
    private final ActiveConnection connection;
    private final UserCollection users;

    public RegisterCommand(ActiveConnection connection, UserCollection users) {
        super("register");
        setAccessLevel(Role.GUEST);
        this.connection = connection;
        this.users = users;
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        return "Нужно использовать execute(String, User)";
    }

    @Override
    public String description() {
        return getName() + " - регистрация нового пользователя.";
    }

    @Override
    public String execute(String argument, UserDTO user, SelectionKey key) {
        try {
            AuthClientContext authClientContext = new AuthClientContext(new User(user.login(), user.password()));
            users.add(authClientContext.getUser());
            connection.connect(authClientContext, key);
            return "Пользователь успешно зарегистрирован";
        } catch (UserCannotBeAdded e) {
            return e.getMessage();
        }
    }

    @Override
    public Class<UserDTO> getType() {
        return UserDTO.class;
    }
}
