package com.tecacet.jflat.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.PropertySetter;
import com.tecacet.jflat.RowRecord;

public class GenericBeanMapper<T> implements BeanMapper<T> {

    private final Supplier<T> beanFactory;
    private final PropertySetter<T> propertySetter;
    private final RecordExtractor recordExtractor;
    private final String[] properties;

    private final Map<String, Function> propertyConverters = new HashMap<>();

    public GenericBeanMapper(Supplier<T> beanFactory, PropertySetter<T> propertySetter, RecordExtractor recordExtractor, String[] properties) {
        this.beanFactory = beanFactory;
        this.propertySetter = propertySetter;
        this.recordExtractor = recordExtractor;
        this.properties = properties;
    }

    @Override
    public T apply(RowRecord record) {
        T bean = beanFactory.get();
        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            if (property == null) {
                continue;
            }
            String token = recordExtractor.getRecordValue(record, i);
            convertAndSet(bean, property, token);
        }
        return bean;
    }

    private void convertAndSet(T bean, String property, String token) {
        Object value = token;
        Function<String, ?> converter = propertyConverters.get(property);
        if (converter != null) {
            value = converter.apply(token);
        }
        propertySetter.setProperty(bean, property, value);
    }

    public <S> void registerConverter(String property, Function<String, S> converter) {
        propertyConverters.put(property, converter);
    }

}
