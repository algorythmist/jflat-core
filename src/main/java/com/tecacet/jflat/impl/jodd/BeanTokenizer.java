package com.tecacet.jflat.impl.jodd;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import com.tecacet.jflat.impl.PropertyGetter;

public class BeanTokenizer<T> implements Function<T, String[]> {

    private final PropertyGetter<T> propertyGetter;
    private final String[] properties;
    private final Map<String,  Function> propertyConverters = new HashMap<>();
    private final Map<Class<?>,  Function> classConverters = new HashMap<>();
    private final Map<String, PropertyGetter<T>> customPropertyGetters = new HashMap<>();

    public BeanTokenizer(String[] properties) {
        this(new JoddPropertyGetter<>(), properties);
    }

    public BeanTokenizer(PropertyGetter<T> getter, String[] properties) {
        this.propertyGetter = getter;
        this.properties = properties;
    }

    @Override
    public String[] apply(T bean) {
        String[] tokens = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            Object value = getProperty(bean, properties[i]);
            tokens[i] = toString(value, properties[i]);
        }
        return tokens;
    }

    private Object getProperty(T bean, String property) {
        PropertyGetter<T> customGetter = customPropertyGetters.get(property);
        if (customGetter != null) {
            return customGetter.getProperty(bean, property);
        }
        return propertyGetter.getProperty(bean, property);
    }

    private String toString(Object value, String property) {
        if (value == null) {
            return null;
        }
        Function<Object, String> converter = getConverter(value, property);
        if (converter == null) {
            return value.toString();
        }
        return converter.apply(value);
    }

    private Function<Object, String> getConverter(Object value, String property) {
        Function<Object, String> converter = propertyConverters.get(property);
        if (converter != null) {
            return converter;
        }
        return classConverters.get(value.getClass()); //TODO support inherritance
    }

    public <S> void registerConverter(String property, Function<S, String> converter) {
        propertyConverters.put(property, converter);
    }

    public <S> void registerConverter(Class<S> clazz, Function<S, String> converter) {
        classConverters.put(clazz, converter);
    }

    public void registerPropertyGetter(String property, PropertyGetter<T> getter) {
        customPropertyGetters.put(property, getter);
    }

    public void registerPropertyGetter(String property, Function<T, Object> getter) {
        registerPropertyGetter(property, (bean, propertyName) -> getter.apply(bean));
    }

}
