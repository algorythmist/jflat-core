# JFlat

JFlat is a Java API that helps developers map flat files to and from Java Objects. 
It includes support for CSV, fixed width format, arbitrarily delimited data, excel, et cetera.

## Getting started with JFlat

### How to read a  file into an list of String arrays

```java
    CSVReader<String[]> reader = CSVReader.createDefaultReader();
    List<String[]> contacts = reader.readAll("contacts.csv");
```

### How to read a file into a list of beans

To read a file without header, just specify the properties of the bean in the locations
where they appear in the file. Use null for columns that you want to skip.

```java
    FlatFileReader<ClassicQuote> csvReader = CSVReader.createWithIndexMapping(ClassicQuote.class,
                new String[]{"date", "open", null, null, "close", "volume", null});
    List<ClassicQuote> quotes = csvReader.readAll("GLD.csv");
```

To read a file with a header, specify the properties and the corresponding names in 
the header

```java
    String[] properties = {"date", "open", "volume"};
    String[] header = {"Date", "Open", "Volume"};

    FlatFileReader<ImmutableQuote> csvReader =
                CSVReader.createWithHeaderMapping(ImmutableQuote.class, header, properties);
    List<ImmutableQuote> quotes = csvReader.readAll("GLD.csv");
```

### More Advanced Features

In the following example, we read a fixed-width file with three fields 
of widths 20,10, and 12. We read the file bean by bean into a Stream
and look for a particular record.

```java
    FixedWidthReader<Contact> reader = FixedWidthReader.createWithIndexMapping(Contact.class,
                new String[] {"name", "address.state", "telephone"},
                new int[] {20,10,12})
                .registerConverter(Telephone.class, s -> new Telephone(s));
    InputStream is = ClassLoader.getSystemResourceAsStream("directory.txt");
    InputStreamReader r = new InputStreamReader(is);
    Contact contact = reader.readAsStream(r)
                .filter(c -> c.getAddress().getState() == Address.State.CA).findFirst()
                .orElse(null);

## Writing to files

TODO
```

