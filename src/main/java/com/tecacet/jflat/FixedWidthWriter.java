package com.tecacet.jflat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import com.tecacet.jflat.impl.AbstractFlatFileWriter;
import com.tecacet.jflat.impl.FixedWidthLineMerger;
import com.tecacet.jflat.impl.BeanTokenizer;

public class FixedWidthWriter<T> extends AbstractFlatFileWriter<T> {

    private final LineMerger lineMerger;

    public FixedWidthWriter(int[] widths, String[] properties) {
        this(new FixedWidthLineMerger(widths), properties);
    }

    public FixedWidthWriter(LineMerger lineMerger, String[] properties) {
        this.lineMerger = lineMerger;
        super.tokenizer = new BeanTokenizer<>(properties);
    }

    @Override
    public void write(Writer writer, Collection<T> beans) throws IOException {
        try (BufferedWriter bf = new BufferedWriter(writer)) {
            for (T bean : beans) {
                if (tokenizer != null) {
                    String[] tokens = tokenizer.apply(bean);
                    String line = lineMerger.makeLine(tokens);
                    bf.write(line);
                } else {
                    bf.write(lineMapper.apply(bean));
                }
            }
        }
    }

}
