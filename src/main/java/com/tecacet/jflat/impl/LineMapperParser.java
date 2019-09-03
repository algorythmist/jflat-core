package com.tecacet.jflat.impl;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import com.tecacet.jflat.FlatFileParser;
import com.tecacet.jflat.RowRecord;

public class LineMapperParser implements FlatFileParser {

    private final LineMapper lineMapper;

    public LineMapperParser(LineMapper lineMapper) {
        this.lineMapper = lineMapper;
    }

    @Override
    public Iterable<RowRecord> parse(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        AtomicLong lineNumber = new AtomicLong(0);
        Iterator<String> iterator = bufferedReader.lines().iterator();

        return () -> new Iterator<RowRecord>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public RowRecord next() {
                long line = lineNumber.incrementAndGet();
                return lineMapper.apply(line, iterator.next());
            }
        };

    }
    
}
