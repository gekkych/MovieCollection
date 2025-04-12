package ru.se.ifmo.s466351.lab6.client.handler;

import ru.se.ifmo.s466351.lab6.common.request.ClientCommandRequest;
import ru.se.ifmo.s466351.lab6.common.request.RequestStatus;
import java.util.Scanner;

public class CommandRequestInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GECKO = "\uD83E\uDD8E";


    public static ClientCommandRequest inputCommandRequest() {
        String commandName = null;
        String argument = null;
        try {
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
}
