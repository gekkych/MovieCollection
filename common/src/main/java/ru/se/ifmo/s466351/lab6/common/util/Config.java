package ru.se.ifmo.s466351.lab6.common.util;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private final int port;
    private final String host;

    public Config(Path configFile) throws ConfigException {
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream(configFile.toFile())) {
            properties.load(in);
            String portStr = properties.getProperty("server.port", "5000").trim();
            host = properties.getProperty("server.host", "localhost").trim();
            try {
                port = Integer.parseInt(portStr);
                if (port < 0 || port > 65535) {
                    throw new ConfigException("Порт не находится в пределах 0-65535: " + "[" + portStr + "]");
                }
            } catch (NumberFormatException e) {
                throw new ConfigException("Невалидный формат порта: " + "[" + portStr + "]");
            }
        } catch (IOException e) {
            throw new ConfigException("Конфиг не найден.");
        }
    }

    public String getConfigHost() {
        return host;
    }

    public int getConfigPort() {
        return port;
    }
}
