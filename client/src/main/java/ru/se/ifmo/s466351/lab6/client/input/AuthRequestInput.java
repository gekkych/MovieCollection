package ru.se.ifmo.s466351.lab6.client.input;

import ru.se.ifmo.s466351.lab6.common.request.*;

import java.util.Scanner;

public class AuthRequestInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GECKO = "\uD83E\uDD8E";
    private static LoginOption option;

    public static Request inputAuthRequest() {
        String login = null;
        String password = null;

        try {
            while (true) {
                System.out.println("Хотите войти или зарегистрироваться? [auth/reg]");
                System.out.print(GECKO + " > ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("auth")) {
                    option = LoginOption.AUTHENTICATION;
                    break;
                }
                if (answer.equals("reg")) {
                    option = LoginOption.REGISTRATION;
                    break;
                }
            }
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
            return switch (option) {
                case AUTHENTICATION -> new ClientAuthenticationRequest(RequestStatus.OK, login, password);
                case REGISTRATION -> new ClientRegistrationRequest(RequestStatus.OK, login, password);
            };
        }
        return new ClientAuthenticationRequest(RequestStatus.ERROR, login, password);
    }
}
