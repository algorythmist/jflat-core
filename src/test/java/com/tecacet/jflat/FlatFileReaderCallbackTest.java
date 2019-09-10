package com.tecacet.jflat;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.domain.Customer;
import com.tecacet.jflat.domain.Order;

class FlatFileReaderCallbackTest {

    @Test
    void testProcessRow() throws IOException {
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
        assertEquals(2, orders.size());

        Order order = orders.get(1);
        Customer customer = order.getCustomer();
        assertEquals("Bob", customer.getFirstName());
        assertEquals("Marley", customer.getLastName());
    }
}
