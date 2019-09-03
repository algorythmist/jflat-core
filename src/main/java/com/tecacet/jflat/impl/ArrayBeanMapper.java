package com.tecacet.jflat.impl;

import java.util.stream.IntStream;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.RowRecord;

public class ArrayBeanMapper implements BeanMapper<String[]> {

	@Override
	public String[] apply(RowRecord record) {
		return IntStream.range(0, record.size()).mapToObj(record::get).toArray(String[]::new);
	}
}
