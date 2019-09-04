package com.tecacet.jflat.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.ConverterRegistry;
import com.tecacet.jflat.FlatFileParser;
import com.tecacet.jflat.FlatFileReader;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.ResourceLoader;
import com.tecacet.jflat.RowRecord;
import com.tecacet.jflat.impl.jodd.JoddConverterRegistry;

public class GenericFlatFileReader<T> implements FlatFileReader<T> {

    private final ConverterRegistry converterRegistry = new JoddConverterRegistry();
    private ResourceLoader resourceLoader = new SequentialResourceLoader(new FileSystemResourceLoader(),
            new ClasspathResourceLoader());

    private BeanMapper<T> beanMapper;
    private FlatFileParser parser;

    public GenericFlatFileReader(BeanMapper<T> beanMapper, FlatFileParser parser) {
        this.beanMapper = beanMapper;
        this.parser = parser;
    }

    @Override
    public void read(String resourceName, FlatFileReaderCallback<T> callback) throws IOException {
        InputStream is = resourceLoader.loadResource(resourceName);
        read(is, callback);
    }

    @Override
    public void read(InputStream is, FlatFileReaderCallback<T> callback) throws IOException {
        InputStreamReader reader = new InputStreamReader(is);
        Iterable<RowRecord> records = parser.parse(reader);
        for (RowRecord record : records) {
            T bean = beanMapper.apply(record);
            callback.accept(record, bean);
        }
    }

    @Override
    public Stream<T> readAsStream(Reader reader) throws IOException {
        return parser.parseStream(reader).map(record -> beanMapper.apply(record));
    }

    @Override
    public List<T> readAll(String resourceName) throws IOException {
        InputStream is = resourceLoader.loadResource(resourceName);
        return readAll(is);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setBeanMapper(BeanMapper<T> beanMapper) {
        this.beanMapper = beanMapper;
    }

    public void setParser(FlatFileParser parser) {
        this.parser = parser;
    }

    @Override
    public <S> FlatFileReader<T> registerConverter(Class<S> type, Function<String, S> converter) {
        converterRegistry.registerConverter(type, converter);
        return this;
    }

    @Override
    public <S> FlatFileReader<T> registerConverter(String property, Function<String, S> converter) {
        if (beanMapper instanceof GenericBeanMapper) {
            ((GenericBeanMapper)beanMapper).registerConverter(property, converter);
        }
        return this;
    }

}
