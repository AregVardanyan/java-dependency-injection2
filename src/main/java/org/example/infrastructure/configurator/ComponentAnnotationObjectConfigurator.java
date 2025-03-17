package org.example.infrastructure.configurator;


import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Component;

public class ComponentAnnotationObjectConfigurator implements ObjectConfigurator{
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> clazz = obj.getClass();
        System.out.println("[Component creating] for " + clazz.getName());
        if(!clazz.isAnnotationPresent(Component.class)){
            throw new IllegalStateException("Not component");
        }
    }
}
