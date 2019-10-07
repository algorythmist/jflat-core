package com.tecacet.jflat.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class LocalDateTimeConverterTest {

    @Test
    void parse() {
        LocalDateTimeConverter converter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");
        assertEquals(LocalDateTime.of(2017, 10,22, 5, 30, 45),
                converter.apply("2017-10-22 05:30:45"));
    }
}
