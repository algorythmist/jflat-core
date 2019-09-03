package com.tecacet.jflat.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.ResourceLoader;

class SequentialResourceLoaderTest {

    @Test
    void loadResourceFromFile() throws IOException {
        ResourceLoader resourceLoader = new SequentialResourceLoader(new FileSystemResourceLoader(),
                new ClasspathResourceLoader());
        InputStream is = resourceLoader.loadResource("src/test/data/resource.txt");
        assertNotNull(is);
    }

    @Test
    void loadResourceFromClasspath() throws IOException {
        ResourceLoader resourceLoader = new SequentialResourceLoader(new FileSystemResourceLoader(),
                new ClasspathResourceLoader());
        InputStream is = resourceLoader.loadResource("resource.txt");
        assertNotNull(is);
    }
}
