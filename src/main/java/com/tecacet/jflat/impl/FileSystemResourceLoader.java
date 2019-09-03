package com.tecacet.jflat.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import com.tecacet.jflat.ResourceLoader;

public class FileSystemResourceLoader implements ResourceLoader {

    @Override
    public InputStream loadResource(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        throw new FileNotFoundException("File does not exist: " + file);
    }
}
