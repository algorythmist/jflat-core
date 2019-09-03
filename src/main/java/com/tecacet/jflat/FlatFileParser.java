package com.tecacet.jflat;

import java.io.IOException;
import java.io.Reader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Parse an input source into a collection of records
 */
public interface FlatFileParser {

    Iterable<RowRecord> parse(Reader reader) throws IOException;

    default Stream<RowRecord> parseStream(Reader reader) throws IOException {
        return StreamSupport.stream(parse(reader).spliterator(), false);
    }
}
