package ru.se.ifmo.s466351.lab6.server.command;

public class ExitCommand extends Command implements Confirmable, Closable {

    public ExitCommand() {
        super("exit");
    }

    @Override
    public String execute(String argument) {
        return "Программа завершена";
    }

    @Override
    public String description() {
        return this.getName() + " - завершение программы (без сохранения файла)";
    }
}
