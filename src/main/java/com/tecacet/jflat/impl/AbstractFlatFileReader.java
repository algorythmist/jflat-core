package com.tecacet.jflat.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.ConverterRegistry;
import com.tecacet.jflat.FlatFileReader;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.ResourceLoader;
import com.tecacet.jflat.impl.jodd.JoddConverterRegistry;

public abstract class AbstractFlatFileReader<T> implements FlatFileReader<T> {

    protected final ConverterRegistry converterRegistry = new JoddConverterRegistry();
    protected ResourceLoader resourceLoader = new SequentialResourceLoader(new FileSystemResourceLoader(),
            new ClasspathResourceLoader());

    @Override
    public void read(String resourceName, FlatFileReaderCallback<T> callback) throws IOException {
        InputStream is = resourceLoader.loadResource(resourceName);
        read(is, callback);
    }

    @Override
    public List<T> readAll(String resourceName) throws IOException {
        InputStream is = resourceLoader.loadResource(resourceName);
        return readAll(is);
    }

    @Override
    public <S> FlatFileReader<T> registerConverter(Class<S> type, Function<String, S> converter) {
        converterRegistry.registerConverter(type, converter);
        return this;
    }

    @Override
    public List<T> readAllWithCallback(String resourceName, FlatFileReaderCallback<T> callback) throws IOException {
        InputStream is = resourceLoader.loadResource(resourceName);
        return readAllWithCallback(is, callback);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
