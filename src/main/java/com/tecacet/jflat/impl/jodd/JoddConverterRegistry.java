package com.tecacet.jflat.impl.jodd;

import java.util.function.Function;
import com.tecacet.jflat.ConverterRegistry;
import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManager;

public class JoddConverterRegistry implements ConverterRegistry {
	
	@Override
	public <C> void registerConverter(Class<C> toType, Function<String, C> converter) {
		TypeConverterManager.get().register(toType, fromFunction(converter));
	}

	public <C> void unregister(Class<C> type) {
		TypeConverterManager.get().unregister(type);
	}

	private static <C> TypeConverter<C> fromFunction(Function<String, C> function) {
		return o -> {
			if (o == null) {
				return null;
			}
			return function.apply(o.toString().trim());
		};
	}
}
