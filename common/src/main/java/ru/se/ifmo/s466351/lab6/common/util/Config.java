package ru.se.ifmo.s466351.lab6.common.util;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private final int PORT;
    private final String HOST;
    private final int MAX_RECONNECT_ATTEMPTS;
    private final int RECONNECT_ATTEMPT_DELAY;

    public Config(Path configFile) throws ConfigException {
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream(configFile.toFile())) {
            properties.load(in);
            HOST = properties.getProperty("HOST", "localhost").trim();
            String portStr = properties.getProperty("PORT", "5000").trim();
            String maxReconnectAttemptsStr = properties.getProperty("CLIENT.MAX_RECONNECT_ATTEMPTS", "3").trim();
            String reconnectAttemptDelayStr = properties.getProperty("CLIENT.RECONNECT_ATTEMPT_DELAY", "2000").trim();
            try {
                PORT = Integer.parseInt(portStr);
                MAX_RECONNECT_ATTEMPTS = Integer.parseInt(maxReconnectAttemptsStr);
                RECONNECT_ATTEMPT_DELAY = Integer.parseInt(reconnectAttemptDelayStr);
                if (PORT < 0 || PORT > 65535) {
                    throw new ConfigException("Порт не находится в пределах 0-65535: " + "[" + portStr + "]");
                }
            } catch (NumberFormatException e) {
                throw new ConfigException("Невалидный формат порта: " + "[" + portStr + "]");
            }
        } catch (IOException e) {
            throw new ConfigException("Конфиг не найден.");
        }
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }

    public int getMAX_RECONNECT_ATTEMPTS() {
        return MAX_RECONNECT_ATTEMPTS;
    }

    public int getRECONNECT_ATTEMPT_DELAY() {
        return RECONNECT_ATTEMPT_DELAY;
    }
}
