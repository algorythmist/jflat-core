package com.tecacet.jflat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tecacet.jflat.domain.Contact;
import com.tecacet.jflat.domain.ContactDataMaker;
import com.tecacet.jflat.domain.Telephone;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class FixedWidthWriterTest {

    @Test
    void write() throws IOException {
        String[] properties = {"name", "address", "telephone", "address.zip"};
        FixedWidthWriter<Contact> fixedWidthWriter = new FixedWidthWriter<>(new int[] {20, 40, 10, 7}, properties);
        //register a custom property getter, which is any Function that takes the target bean and returns a String
        fixedWidthWriter.registerPropertyGetter("name", contact -> contact.getLastName() + ", " + contact.getFirstName());
        //Register a custom String converter for the type Telephone. In this case, we want the number without the area code
        fixedWidthWriter.registerConverterForClass(Telephone.class, Telephone::getNumber);
        //Register a custom converter for a property by name. In this case we want to enclose the zip code in parenthesis
        Function<Integer, String> zipConverter = i -> "(" + i + ")";
        fixedWidthWriter.registerConverterForProperty("address.zip", zipConverter);
        Contact contact1 = ContactDataMaker.createContact("Leo", "Tolstoy", "3901234900", "Polyana");
        Contact contact2 = ContactDataMaker.createContact("Anna", "Karenina", "9007862121", "Liverpool");
        List<Contact> contacts = Arrays.asList(contact1, contact2);
        fixedWidthWriter.writeToFile("contacts.txt", contacts);

        File file = new File("contacts.txt");
        assertTrue(file.exists());
        List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
        assertEquals(2, lines.size());
        file.delete();
    }
}
