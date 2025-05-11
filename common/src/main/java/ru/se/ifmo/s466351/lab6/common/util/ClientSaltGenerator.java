package ru.se.ifmo.s466351.lab6.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ClientSaltGenerator {
    public static String  generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
