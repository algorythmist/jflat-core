package com.tecacet.jflat.impl;

public interface PropertySetter<T> {

    void setProperty(T bean, String property, String value);
}
