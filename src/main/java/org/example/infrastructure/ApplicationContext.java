package org.example.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.example.infrastructure.configreader.ObjectConfigReader;

import java.util.*;

public class ApplicationContext {

    @Setter
    private ObjectFactory objectFactory;

    @Getter
    private ObjectConfigReader objectConfigReader;


    public ApplicationContext(ObjectConfigReader objectConfigReader) {
        this.objectConfigReader = objectConfigReader;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> cls) {
        Class<? extends T> implClass = objectConfigReader.getImplClass(cls);

        T object = objectFactory.createObject(implClass);

        return object;
    }
}
