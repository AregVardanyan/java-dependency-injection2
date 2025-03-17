package org.example.infrastructure.manager;


import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private static final Map<String, Object> cache = new HashMap<>();

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
