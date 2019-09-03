package com.tecacet.jflat.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import com.tecacet.jflat.FlatFileParser;
import com.tecacet.jflat.RowRecord;

public class CSVFileParser implements FlatFileParser {

    private final CSVFormat csvFormat;

    public CSVFileParser(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    @Override
    public Iterable<RowRecord> parse(Reader reader) throws IOException {

        CSVParser parser = csvFormat.parse(reader);
        return () -> new Iterator<RowRecord>() {
            @Override
            public boolean hasNext() {
                return parser.iterator().hasNext();
            }

            @Override
            public RowRecord next() {
                return new CSVRowRecord(parser.iterator().next());
            }
        };
    }

}
