package com.tecacet.jflat.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class LocalDateConverter implements Function<String, LocalDate> {

    private static final String[] STANDARD_FORMATS = { "M/d/yyyy", "M-d-yyyy", "yyyy-M-d" };

    private final DateTimeFormatter[] formatters;

    public LocalDateConverter() {
        this(STANDARD_FORMATS);
    }

    public LocalDateConverter(String... dateFormatStrings) {
        super();
        this.formatters = new DateTimeFormatter[dateFormatStrings.length];
        for (int i = 0; i < dateFormatStrings.length; i++) {
            formatters[i] = DateTimeFormatter.ofPattern(dateFormatStrings[i]);
        }
    }


    @Override
    public LocalDate apply(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(string.trim(), formatter);
            } catch (Exception e) {
                // try the next formatter
            }
        }
        String message = String.format("Error converting %s to LocalDate", string);
        throw new IllegalArgumentException(message);
    }
}
