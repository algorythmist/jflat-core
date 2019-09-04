package com.tecacet.jflat;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.domain.Contact;
import com.tecacet.jflat.domain.ContactDataMaker;
import com.tecacet.jflat.domain.Telephone;

class FixedWidthWriterTest {

    @Test
    void write() throws IOException {
        String[] properties = {"name", "address", "telephone", "address.zip"};
        FixedWidthWriter<Contact> fixedWidthWriter = new FixedWidthWriter<>(new int[] {20, 40, 10, 7},
                properties);
        fixedWidthWriter.registerPropertyGetter("name" ,
                contact -> contact.getLastName() + ", " + contact.getFirstName());
        Function<Telephone, String> telephoneConverter = Telephone::getNumber;
        fixedWidthWriter.registerConverterForClass(Telephone.class, telephoneConverter);
        Function<Integer, String> zipConverter = i -> "("+i+")";
        fixedWidthWriter.registerConverterForProperty("address.zip", zipConverter);
        Contact contact1 = ContactDataMaker.createContact("Leo", "Tolstoy",
                "3901234900", "Polyana");
        Contact contact2 = ContactDataMaker.createContact("Anna", "Karenina",
                "9007862121", "Liverpool");
        List<Contact> contacts = Arrays.asList(contact1, contact2);
        fixedWidthWriter.writeToFile("contacts.txt", contacts);

        File file = new File("contacts.txt");
        assertTrue(file.exists());
        List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
        assertEquals(2, lines.size());
        file.delete();
    }
}
