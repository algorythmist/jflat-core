package com.tecacet.jflat.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class LocalTimeConverterTest {

	@Test
	void test() {
		LocalTimeConverter converter = new LocalTimeConverter("HH:mm:ss");
		assertEquals(LocalTime.of(5, 30, 45), converter.apply("05:30:45"));
	}

}
