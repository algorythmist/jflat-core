package com.tecacet.jflat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.domain.Address;
import com.tecacet.jflat.domain.Contact;
import com.tecacet.jflat.domain.Telephone;

class FixedWidthReaderTest {

    @Test
    void testDefaultReader() throws IOException {
        FixedWidthReader<String[]> reader = FixedWidthReader.createDefaultReader(new int[]{20, 10, 12});
        List<String[]> items = reader.readAll("directory.txt");
        items.forEach(c -> System.out.println(Arrays.toString(c)));
    }

    @Test
    void testIndexedReader() throws IOException {
        FixedWidthReader<Contact> reader = FixedWidthReader.createWithIndexMapping(
                Contact.class,
                new String[]{"name", "address.state", "telephone"},
                new int[]{20, 10, 12})
                .withSkipRows(1)
                .registerConverter(Telephone.class, s -> new Telephone(s));
        List<Contact> contacts = reader.readAllWithCallback("directory.txt", (record, contact) -> {
            String[] fullName = record.get(0).trim().split("\\s+");
            contact.setFirstName(fullName[0]);
            contact.setLastName(fullName[1]);
        });
        assertEquals(3, contacts.size());
        Contact contact = contacts.get(1);
        assertEquals("Mary", contact.getFirstName());
        assertEquals("Hartford", contact.getLastName());
        assertEquals("(319) 5194341", contact.getTelephone().toString());
        assertEquals(Address.State.CA, contact.getAddress().getState());
    }

    @Test
    public void readAsStream() throws IOException {
        FixedWidthReader<Contact> reader = FixedWidthReader.createWithIndexMapping(Contact.class,
                new String[]{"name", "address.state", "telephone"},
                new int[]{20, 10, 12})
                .registerConverter(Telephone.class, s -> new Telephone(s));
        InputStream is = ClassLoader.getSystemResourceAsStream("directory.txt");
        InputStreamReader r = new InputStreamReader(is);
        Contact contact = reader.readAsStream(r)
                .filter(c -> c.getAddress().getState() == Address.State.CA).findFirst()
                .orElse(null);
        assertEquals(Address.State.CA, contact.getAddress().getState());
        assertEquals("(319) 5194341", contact.getTelephone().toString());
        //TODO assertEquals("Mary Hartford", contact.getName());
        r.close();
    }
}
