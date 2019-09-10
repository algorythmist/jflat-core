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

For CSV support, JFlat uses [Apache Commons CSV](http://commons.apache.org/proper/commons-csv/)
so all the features provided by this library are available, simply by providing the 
appropriate CSV Format.

The following example specifies a CSV format to read a tab-delimited file:

```java
    String[] properties = {"firstName", "lastName", "telephone",
                "address.numberAndStreet", "address.city", "address.state", "address.zip"};
    CSVReader<Contact> csvReader = CSVReader.createWithIndexMapping(Contact.class, properties)
                .withFormat(CSVFormat.TDF)
                .registerConverter(Telephone.class, Telephone::new);
    List<Contact> contacts = csvReader.readAll("contacts.tdf");
```

## Writing to files

In the following example, we map Contact objects in a CSV file. 
We specify the properties we want to map and the header. 
Furthermore, we register a converter that converts objects of Telephone to String.
If we do not specify a converter, the toString() method is going to be used.

```java
    String[] properties = {"firstName", "lastName", "telephone",
                "address.numberAndStreet", "address.city", "address.state", "address.zip"};
    String[] header = {"First Name", "Last Name", "Phone", "Street", "City", "State", "Zip" };
    CSVWriter csvWriter = CSVWriter
                .createForProperties(properties)
                .withHeader(header)
                .registerConverterForClass(Telephone.class, telephone -> telephone.getNumber()+telephone.getAreaCode());
    csvWriter.writeToFile("contacts.csv", contacts);   
```

In the example below, we write Contact objects in a Fixed-width file:

```java
    String[] properties = {"name", "address", "telephone", "address.zip"};
    FixedWidthWriter<Contact> fixedWidthWriter = new FixedWidthWriter<>(new int[] {20, 40, 10, 7},
                properties);
    List<Contact> contacts = Arrays.asList(contact1, contact2);
    fixedWidthWriter.writeToFile("contacts.txt", contacts);
```


## Advanced Features

### Reading records in a Java 8 Stream

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
```

### Reading with a callback

The beans do not need to be read directly in a list or a stream. 
It is possible to process each bean as it is being read by using a FlatFileReaderCallback.
This enables highly customizable mappings from files to beans.

In the following example, we read an orders file where the Customers name appears in a single field. 
We use a FlatFileReaderCallback to parse the complete name into first and last name:

```java
    List<Order> orders = new ArrayList<>();
    FlatFileReaderCallback<Order> callback = (record, order) -> {
            String[] name = record.get(1).split(",");
            String lastName = name[0];
            String firstName = name[1];
            order.setCustomer(new Customer());
            order.getCustomer().setLastName(lastName);
            order.getCustomer().setFirstName(firstName);
            orders.add(order);
    };

    String[] properties = new String[] { "number", "price" };
    String[] header = new String[] { "Number", "Price" };
    CSVReader<Order> csvReader = CSVReader.createWithHeaderMapping(Order.class, header, properties);
    csvReader.read("orders.csv", callback);
```
