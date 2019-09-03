package com.tecacet.jflat.impl;

import java.util.function.Supplier;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.RowRecord;

public class GenericBeanMapper<T> implements BeanMapper<T> {

    private final Supplier<T> beanFactory;
    private final PropertySetter<T> propertySetter;
    private final RecordExtractor recordExtractor;
    private final String[] properties;

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
            propertySetter.setProperty(bean, property, token);
        }
        return bean;
    }

}
