package com.tecacet.jflat;

import java.util.function.Function;
import com.tecacet.jflat.impl.ArrayBeanMapper;
import com.tecacet.jflat.impl.FixedWidthLineMapper;
import com.tecacet.jflat.impl.GenericFlatFileReader;
import com.tecacet.jflat.impl.IndexBeanMapper;
import com.tecacet.jflat.impl.LineMapperParser;

public class FixedWidthReader<T> extends GenericFlatFileReader<T> {

    private final int[] widths;

    public FixedWidthReader(BeanMapper<T> beanMapper, int[] widths) {
        super(beanMapper, new LineMapperParser(new FixedWidthLineMapper(widths)));
        this.widths = widths;
    }

    public static FixedWidthReader<String[]> createDefaultReader(int[] widths) {
        return new FixedWidthReader<>(new ArrayBeanMapper(), widths);
    }

    public static <T> FixedWidthReader<T> createWithIndexMapping(Class<T> type, String[] properties, int[] widths) {
        return new FixedWidthReader<T>(new IndexBeanMapper<T>(type, properties), widths);
    }

    public FixedWidthReader<T> withSkipRows(int skipRows) {
        super.setParser(new LineMapperParser(new FixedWidthLineMapper(widths), skipRows));
        return this;
    }

    @Override
    public <S> FixedWidthReader<T> registerConverter(Class<S> type, Function<String, S> converter) {
        super.registerConverter(type, converter);
        return this;
    }
}
