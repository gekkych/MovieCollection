package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.user.Role;

import java.nio.channels.SelectionKey;

public class ExitCommand extends Command implements Closable {

    public ExitCommand() {
        super("exit");
        setAccessLevel(Role.GUEST);
    }

    @Override
    public String execute(String argument, SelectionKey key) {
        return "Программа завершена";
    }

    @Override
    public String description() {
        return this.getName() + " - завершение программы (без сохранения файла)";
    }
}
