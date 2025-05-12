package ru.se.ifmo.s466351.lab6.client.input;

import ru.se.ifmo.s466351.lab6.common.dto.UserDTO;
import ru.se.ifmo.s466351.lab6.common.request.*;

import java.util.Scanner;

public class AuthRequestInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GECKO = "\uD83E\uDD8E";

    public static Request inputAuthRequest() {
        String login = null;
        String password = null;

        try {
            System.out.println("Введите логин");
            System.out.print(GECKO + " > ");
            login = scanner.nextLine().trim();

            System.out.println("Введите пароль");
            System.out.print(GECKO + " > ");
            password = scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (login != null && password != null) {
            return new ClientUserDataRequest(RequestStatus.OK, new UserDTO(login, password));
        }
        return new ClientUserDataRequest(RequestStatus.ERROR, new UserDTO(login, password));
    }
}
