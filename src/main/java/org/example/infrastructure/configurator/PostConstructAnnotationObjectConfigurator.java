package org.example.infrastructure.configurator;

import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.PostConstruct;

import java.lang.reflect.Method;

public class PostConstructAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Method[] methods = obj.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(obj);
                    System.out.println("[PostConstruct]: for " + method.getName());
                } catch (Exception e) {
                    throw new RuntimeException("Error invoking @PostConstruct method: " + method.getName(), e);
                }
            }
        }
    }
}
