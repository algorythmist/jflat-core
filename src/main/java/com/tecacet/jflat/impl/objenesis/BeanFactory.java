package com.tecacet.jflat.impl.objenesis;

import java.util.function.Supplier;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * Instantiate beans of a specific type
 * If Class.newInstance() fails, it instantiates a proxy with Objenesis
 * 
 * 
 * @author dimitri
 *
 * @param <T> the type of bean
 */
public class BeanFactory<T> implements Supplier<T> {

	private final Objenesis objenesis = new ObjenesisStd();
	private final Class<T> type;

	public BeanFactory(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T get() {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return objenesis.newInstance(type);
		}
	}

}
