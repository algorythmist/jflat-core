package com.tecacet.jflat.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.tecacet.jflat.RowRecord;

public class RegexLineMapperTest {

	@Test
	void testDelimiter() {
		LineMapper lineMapper = new RegexLineMapper("\\|");
		RowRecord record = lineMapper.apply(1L, "ABCD|78|&%");
		assertEquals(3, record.size());
		assertEquals("ABCD", record.get(0));
		assertEquals("78", record.get(1));
		assertEquals("&%", record.get(2));
	}

	@Test
	void testWords() {
		LineMapper lineMapper = new RegexLineMapper("\\s+");
		RowRecord record = lineMapper.apply(1L, "The quick brown fox jumped over the lazy dog.");
		assertEquals(9, record.size());
		assertEquals("[The, quick, brown, fox, jumped, over, the, lazy, dog.]", record.toString());
	}

}
