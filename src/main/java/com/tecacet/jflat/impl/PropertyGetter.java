package com.tecacet.jflat.impl;

/**
 * Abstraction for getting object properties
 *
 * @author Dimitri Papaioannou
 *
 * @param <T>
 */
public interface PropertyGetter<T> {

    /**
     * Get a named property value from a bean Return NULL if any object is NULL
     * when trying to get the specified property
     *
     * @param bean target bean
     * @param propertyName name of the property to access
     */
    Object getProperty(T bean, String propertyName);
}
