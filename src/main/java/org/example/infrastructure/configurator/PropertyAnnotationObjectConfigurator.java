package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Env;
import org.example.infrastructure.annotation.Property;
import org.example.infrastructure.configloader.ConfigLoader;
import org.example.infrastructure.configloader.JavaConfigLoader;

import java.lang.reflect.Field;

public class PropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        ConfigLoader configLoader = new JavaConfigLoader();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                Property propertyAnnotation = field.getAnnotation(Property.class);
                String propertyName = propertyAnnotation.value().isEmpty() ? field.getName() : propertyAnnotation.value();
                String propertyValue = configLoader.getProperty(propertyName);

                if (propertyValue == null) {
                    throw new RuntimeException("Property variable not found: " + propertyName);
                }

                field.setAccessible(true);
                System.out.println("[Property value call ]"+ propertyValue + " property value set for " + field.getName());

                field.set(obj, propertyValue);
            }
        }
    }
}
