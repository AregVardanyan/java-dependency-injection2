package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;
import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.manager.CacheManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class AnnotationProxyWrapper implements ProxyWrapper {

    private boolean hasCacheAnnotatedMethod(Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Cacheable.class)) {
                return true;
            }
        }
        return false;
    }
    private boolean hasLogAnnotatedMethod(Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {
        if (!cls.isAnnotationPresent(Log.class) && !hasLogAnnotatedMethod(obj.getClass()) && !hasCacheAnnotatedMethod(obj.getClass())) {
            return obj;
        }
        if (cls.getInterfaces().length != 0 && !cls.isAnnotationPresent(Log.class)) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.printf(
                                    "[Logging method]: %s. Args: %s\n", method.getName(), Arrays.toString(args));

                            return method.invoke(obj, args);
                        }
                    }
            );
        }
        return (T) Enhancer.create(
                cls,
                new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {


                        if (method.isAnnotationPresent(Cacheable.class)) {
                            String cacheKey = getCacheKey(method, args);
                            if (cacheKey != null) {
                                if (CacheManager.containsKey(cacheKey)) {
                                    System.out.println("[CacheInformation]: Cache was set for key: " + cacheKey);
                                    return CacheManager.get(cacheKey); // Return cached value
                                }
                            }

                            Object result = method.invoke(obj, args);

                            if (cacheKey != null) {
                                System.out.println("[CacheInformation]: Cache miss for key: " + cacheKey);
                                CacheManager.put(cacheKey, result);
                            }
                        }
                        if (method.isAnnotationPresent(Log.class)) {
                            System.out.printf("[Logging method]: %s. Args: %s\n", method.getName(), Arrays.toString(args));
                        }
                        return method.invoke(obj, args);
                    }

                }
        );
    }
    private static String getCacheKey(Method method, Object[] args) {
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            if (parameter.isAnnotationPresent(CacheKey.class)) {
                return args[i].toString();
            }
        }
        return null;
    }
}
