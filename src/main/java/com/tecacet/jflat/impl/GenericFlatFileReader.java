package com.tecacet.jflat.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Function;
import java.util.stream.Stream;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.FlatFileParser;
import com.tecacet.jflat.FlatFileReader;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.RowRecord;

public class GenericFlatFileReader<T> extends AbstractFlatFileReader<T> {

    private BeanMapper<T> beanMapper;
    private FlatFileParser parser;

    public GenericFlatFileReader(BeanMapper<T> beanMapper, FlatFileParser parser) {
        this.beanMapper = beanMapper;
        this.parser = parser;
    }

    @Override
    public void read(InputStream is, FlatFileReaderCallback<T> callback) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(is)) {
            Iterable<RowRecord> records = parser.parse(reader);
            for (RowRecord record : records) {
                T bean = beanMapper.apply(record);
                callback.accept(record, bean);
            }
        }
    }

    @Override
    public Stream<T> readAsStream(Reader reader) throws IOException {
        return parser.parseStream(reader).map(record -> beanMapper.apply(record));
    }


    @Override
    public <S> FlatFileReader<T> registerConverter(String property, Function<String, S> converter) {
        if (beanMapper instanceof GenericBeanMapper) {
            ((GenericBeanMapper) beanMapper).registerConverter(property, converter);
        }
        return this;
    }

    public void setParser(FlatFileParser parser) {
        this.parser = parser;
    }

    public void setBeanMapper(BeanMapper<T> beanMapper) {
        this.beanMapper = beanMapper;
    }
}
