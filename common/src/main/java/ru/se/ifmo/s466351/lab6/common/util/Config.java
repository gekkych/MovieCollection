package ru.se.ifmo.s466351.lab6.common.util;

import ru.se.ifmo.s466351.lab6.common.exception.ConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config(Path configPath) throws IOException {
        properties = new Properties();


        if (Files.exists(configPath)) {
            try {
                properties.load(Files.newInputStream(configPath));
            } catch (IOException e) {
                throw new IOException("Ошибка при чтении конфигурационного файла.", e);
            }
        } else {
            System.out.println("Конфигурационный файл не найден, используются значения по умолчанию.");
            loadDefaults();
        }
    }

    private void loadDefaults() {
        properties.setProperty("HOST", "localhost");
        properties.setProperty("PORT", "8080");
    }

    public String getHost() {
        return properties.getProperty("HOST");
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("PORT"));
    }
}

