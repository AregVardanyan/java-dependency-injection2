package org.example.infrastructure.configurator;

import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.enums.ScopeType;
import org.example.infrastructure.manager.ScopeManager;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeAnnotationObjectConfigurator implements ObjectConfigurator{

    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> clazz = obj.getClass();
        if(clazz.isAnnotationPresent(Scope.class)){
            Scope scope = clazz.getAnnotation(Scope.class);
            if (scope.value() == ScopeType.SINGLETON){
                ScopeManager.request(clazz, ScopeType.SINGLETON);
            }
            if (scope.value() == ScopeType.PROTOTYPE){
                ScopeManager.request(clazz, ScopeType.PROTOTYPE);
            }
        }
    }

}
