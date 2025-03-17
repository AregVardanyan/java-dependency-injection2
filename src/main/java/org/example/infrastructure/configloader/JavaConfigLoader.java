package org.example.infrastructure.configloader;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class JavaConfigLoader implements ConfigLoader {

    private static final Properties properties = new Properties();

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public JavaConfigLoader() {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
}
