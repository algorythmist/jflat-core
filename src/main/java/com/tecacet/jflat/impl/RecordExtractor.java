package com.tecacet.jflat.impl;

import com.tecacet.jflat.RowRecord;

public interface RecordExtractor {
    /**
     * Extact the value at index
     * @param record the record representing a row of data
     * @param index the index of the property in the row
     * @return the value at the index
     */
    String getRecordValue(RowRecord record, int index);
}
