package com.tecacet.jflat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.domain.ClassicQuote;
import com.tecacet.jflat.domain.ImmutableQuote;

class CSVReaderTest {

    @Test
    void testDefaultReader() throws IOException {
        CSVReader<String[]> reader = CSVReader.createDefaultReader()
                .withFormat(CSVFormat.RFC4180
                        .withFirstRecordAsHeader()
                        .withSkipHeaderRecord(true));
        List<String[]> contacts = reader.readAll("contacts.csv");
        contacts.forEach(c -> System.out.println(Arrays.toString(c)));
    }

    @Test
    void testClassicQuote() throws IOException {
        FlatFileReader<ClassicQuote> csvReader = CSVReader.createWithIndexMapping(ClassicQuote.class,
                new String[]{"date", "open", null, null, "close", "volume", null});
        List<ClassicQuote> quotes = csvReader.readAll("GLD.csv");
        assertEquals(134, quotes.size());
        System.out.println(quotes.get(10));
    }

    @Test
    public void readAsStream() throws IOException {
        FlatFileReader<ImmutableQuote> csvReader = CSVReader.createWithIndexMapping(ImmutableQuote.class,
                new String[]{"date", "open", null, null, "close", "volume", "adjustedClose"});
        LocalDate date = LocalDate.of(2015, 5, 1);
        InputStream is = ClassLoader.getSystemResourceAsStream("GLD.csv");
        ImmutableQuote quote = csvReader.readAsStream(is)
                .filter(q -> q.getDate().equals(date)).findFirst()
                .orElse(null);
        assertEquals(date, quote.getDate());
    }

    @Test
    public void readWithCallback() throws IOException {
        FlatFileReader<ImmutableQuote> csvReader = CSVReader.createWithIndexMapping(ImmutableQuote.class,
                new String[]{"date", "open", null, null, "close", "volume", "adjustedClose"});

        List<RowRecord> records = new ArrayList<>();
        csvReader.read("GLD.csv", (row, bean) -> records.add(row));
        assertEquals(134, records.size());
    }

    @Test
    public void readWithHeaderMapping() throws IOException {
        String[] properties = {"date", "open", "volume"};
        String[] header = {"Date", "Open", "Volume"};

        FlatFileReader<ImmutableQuote> csvReader =
                CSVReader.createWithHeaderMapping(ImmutableQuote.class, header, properties);
        List<ImmutableQuote> quotes = csvReader.readAll("GLD.csv");
        assertEquals(134, quotes.size());
        ImmutableQuote quote = quotes.get(0);
        assertEquals(LocalDate.of(2015, 12, 1), quote.getDate());
        assertEquals(102.30, quote.getOpen().doubleValue(), 0.001);
        assertEquals(5800200L, quote.getVolume().longValue());
    }

    @Test
    public void testReadWithConverter() {
        String[] properties = {"name", "lastName", "volume"};
        String[] header = {"Date", "Open", "Volume"};

    }
}
