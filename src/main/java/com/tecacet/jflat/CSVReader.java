package com.tecacet.jflat;

import org.apache.commons.csv.CSVFormat;
import com.tecacet.jflat.impl.ArrayBeanMapper;
import com.tecacet.jflat.impl.CSVFileParser;
import com.tecacet.jflat.impl.GenericFlatFileReader;
import com.tecacet.jflat.impl.HeaderBeanMapper;
import com.tecacet.jflat.impl.IndexBeanMapper;

public class CSVReader<T> extends GenericFlatFileReader<T> {

    public CSVReader(BeanMapper<T> beanMapper) {
        super(beanMapper, new CSVFileParser(CSVFormat.DEFAULT));
    }

    public CSVReader<T> withResourceLoader(ResourceLoader resourceLoader) {
        setResourceLoader(resourceLoader);
        return this;
    }

    public CSVReader<T> withFormat(CSVFormat csvFormat) {
        setParser(new CSVFileParser(csvFormat));
        return this;
    }

    public static CSVReader<String[]> createDefaultReader() {
        return new CSVReader<>(new ArrayBeanMapper());
    }

    public static <T> CSVReader<T> createWithIndexMapping(Class<T> type, String[] properties) {
        return new CSVReader<>(new IndexBeanMapper<>(type, properties))
                .withFormat(CSVFormat.DEFAULT.withFirstRecordAsHeader().withSkipHeaderRecord());
    }

    public static <T> CSVReader<T> createWithHeaderMapping(Class<T> type,
                                                           String[] header,
                                                           String[] properties) {
        return new CSVReader<>(new HeaderBeanMapper<>(type, header, properties))
                .withFormat(CSVFormat.DEFAULT.withFirstRecordAsHeader());
    }

}
