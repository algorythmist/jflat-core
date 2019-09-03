package com.tecacet.jflat.impl.jodd;

import com.tecacet.jflat.impl.PropertyGetter;
import jodd.bean.BeanUtil;

public class JoddPropertyGetter<T> implements PropertyGetter<T> {

    private final BeanUtil beanUtil;

    public JoddPropertyGetter() {
        this(BeanUtil.declaredForcedSilent);
    }
    public JoddPropertyGetter(BeanUtil beanUtil) {
        this.beanUtil = beanUtil;
    }

    @Override
    public Object getProperty(T bean, String propertyName) {
        return beanUtil.getProperty(bean, propertyName);
    }
}
