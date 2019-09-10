package com.tecacet.jflat.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.RowRecord;
import com.tecacet.jflat.domain.Order;
import com.tecacet.jflat.impl.jodd.JoddPropertySetter;
import com.tecacet.jflat.impl.objenesis.BeanFactory;

class GenericBeanMapperTest {

    @Test
    void testGetSimpleRow() {

        BeanMapper<Order> mapper = new GenericBeanMapper(new BeanFactory<>(Order.class),
                new JoddPropertySetter(),
                new IndexRecordExtractor(new int[] {0,1,2}),
                //TODO new HeaderRecordExtractor(new String[]{"Number", "Price", "Quantity"}),
                new String[]{"number", "price", "quantity"});

        RowRecord record = new ArrayRowRecord(1, new String[]{"911", "12.5", "100"});

        // First row gets the header
        Order order = mapper.apply(record);
        assertEquals(100, order.getQuantity());
        assertEquals("911", order.getNumber());
        assertEquals(12.5, order.getPrice(), 0.0001);
        assertNull(order.getCustomer());

    }

}
