package com.tecacet.jflat.impl;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import com.tecacet.jflat.FlatFileParser;
import com.tecacet.jflat.RowRecord;

public class LineMapperParser implements FlatFileParser {

    private final LineMapper lineMapper;
    private final int skipRows;

    public LineMapperParser(LineMapper lineMapper) {
        this(lineMapper, 0);
    }

    public LineMapperParser(LineMapper lineMapper, int skipRows) {
        this.lineMapper = lineMapper;
        this.skipRows = skipRows;
    }

    @Override
    public Iterable<RowRecord> parse(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        AtomicLong lineNumber = new AtomicLong(0);
        Iterator<String> iterator = bufferedReader.lines().iterator();
        skipRows(iterator);

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

    private void skipRows(Iterator<String> iterator) {
        int rows = 0;
        while (iterator.hasNext() && rows < skipRows) {
            iterator.next();
            rows++;
        }
    }
}
