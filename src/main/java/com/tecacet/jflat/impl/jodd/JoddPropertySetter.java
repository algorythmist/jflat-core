package com.tecacet.jflat.impl.jodd;

import com.tecacet.jflat.impl.PropertySetter;
import jodd.bean.BeanUtil;

public class JoddPropertySetter<T> implements PropertySetter<T> {

    private final BeanUtil beanUtil;

    public JoddPropertySetter() {
        this(BeanUtil.declaredForcedSilent);
    }
    public JoddPropertySetter(BeanUtil beanUtil) {
        this.beanUtil = beanUtil;
    }

    @Override
    public void setProperty(T bean, String property, String value) {
        beanUtil.setProperty(bean, property, value);
    }
}
