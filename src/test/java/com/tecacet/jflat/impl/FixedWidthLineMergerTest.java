package com.tecacet.jflat.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.exception.FieldTooWideException;
import com.tecacet.jflat.exception.LineMergerException;
import com.tecacet.jflat.exception.TooManyFieldsException;

class FixedWidthLineMergerTest {

    @Test
    public void testMakeLine() {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        String line = merger.makeLine(new String[] { "a", "b" });
        assertEquals("a b    \n", line); //Left justified!
    }

    @Test
    public void testTooManyFields()  {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        Assertions.assertThrows(TooManyFieldsException.class, () -> {
            merger.makeLine(new String[]{"12", "12345", "123456"});
        });
    }

    @Test
    public void testFieldTooWide() throws LineMergerException {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        Assertions.assertThrows(FieldTooWideException.class, () -> {
            merger.makeLine(new String[]{"12", "123456"});
        });
    }

    @Test
    public void testFieldTooWideTruncate() throws LineMergerException {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        merger.setTruncateFields(true);
        String line = merger.makeLine(new String[]{"12", "123456"});
        assertEquals("1212345\n", line);
    }

    @Test
    public void testNullFields() {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 1, 1, 1 });
        String line = merger.makeLine(new String[] { "a", null, "c" });
        assertEquals("a c\n", line);
    }

}
