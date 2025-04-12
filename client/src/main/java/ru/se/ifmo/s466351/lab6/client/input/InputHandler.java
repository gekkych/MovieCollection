package ru.se.ifmo.s466351.lab6.client.input;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;
    private static final String GECKO = "\uD83E\uDD8E";

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public ClientCommandRequest inputCommandRequest() {
        String commandName = null;
        String argument = null;
        try (scanner) {
            System.out.print(GECKO + " > ");
            String[] input = scanner.nextLine().trim().split(" ", 2);
            commandName = input[0];
            argument = input.length > 1 ? input[1] : "";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (commandName != null && argument != null) {
            return new ClientCommandRequest(RequestStatus.OK, commandName, argument);
        } else return new ClientCommandRequest(RequestStatus.ERROR, commandName, argument);
    }

    private boolean confirmCommand(String commandName) {
        try {
            System.out.println("Вы уверены, что хотите выполнить команду " + commandName + "? (y/n)");
            System.out.print(GECKO + " > ");
            String input = scanner.nextLine().trim().toLowerCase();
            return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
