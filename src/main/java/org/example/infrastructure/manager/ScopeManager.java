package org.example.infrastructure.manager;

import lombok.SneakyThrows;
import org.example.infrastructure.enums.ScopeType;

import java.util.HashMap;
import java.util.Map;

public class ScopeManager {
    private final static Map<Class<?>, Object> singletonObjects = new HashMap<>();
    private final static Map<Class<?>, Object> prototypeObjects = new HashMap<>();

    @SneakyThrows
    public static Object request(Class<?> cls, ScopeType scopeType){
        if (scopeType == ScopeType.SINGLETON) {
            return handleSingleton(cls);
        } else if (scopeType == ScopeType.PROTOTYPE) {
            return handlePrototype(cls);
        }

        throw new IllegalStateException("Unsupported scope type: " + scopeType);
    }


    private static <T> T handleSingleton(Class<T> clazz) {
        if (singletonObjects.containsKey(clazz)) {
            throw new IllegalStateException("Error: Singleton instance of " + clazz.getName() + " already exists.");
        }

        T instance = createInstance(clazz);
        singletonObjects.put(clazz, instance);
        return instance;
    }

    private static <T> T handlePrototype(Class<T> clazz) {
        T instance = createInstance(clazz);
        prototypeObjects.put(clazz, instance);
        return instance;
    }

    private static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }

}
