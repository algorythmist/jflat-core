package com.tecacet.jflat.impl;

import com.tecacet.jflat.RowRecord;

public interface RecordExtractor {

    String getRecordValue(RowRecord record, int index);
}
