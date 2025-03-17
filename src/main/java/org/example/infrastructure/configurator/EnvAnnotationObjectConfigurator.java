package org.example.infrastructure.configurator;


import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Env;
import org.example.infrastructure.configloader.ConfigLoader;
import org.example.infrastructure.configloader.JavaConfigLoader;

import java.lang.reflect.Field;

public class EnvAnnotationObjectConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Env.class)) {
                Env envAnnotation = field.getAnnotation(Env.class);
                String envVarName = envAnnotation.value().isEmpty() ? field.getName() : envAnnotation.value();
                String envValue = System.getenv(envVarName);

                if (envValue == null) {
                    throw new RuntimeException("Environment variable not found: " + envVarName);
                }

                field.setAccessible(true);
                System.out.println("[Env variable call]: " + envValue + " envValue set for " + field.getName());

                field.set(obj, envValue);
            }
        }
    }
}
