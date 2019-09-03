package com.tecacet.jflat.impl;

import java.util.function.BiFunction;
import com.tecacet.jflat.RowRecord;

/**
 * Map a line to a tokenized record
 * 
 * @author dimitri
 *
 */
@FunctionalInterface
public interface LineMapper extends BiFunction<Long, String, RowRecord> {

}
