package com.tecacet.jflat.exception;

public class TooManyFieldsException extends LineMergerException {

    private final int fieldCount;
    private final int maxFieldCount;
    
    public TooManyFieldsException(int fieldCount, int maxFieldCount) {
        super();
        this.fieldCount = fieldCount;
        this.maxFieldCount = maxFieldCount;
    }
    
    @Override
    public String getMessage(){
        String message = String.format("Too many fields.  Got: %d, Maximum: %d.", fieldCount, maxFieldCount);
        return message;
    }
    
    public int getFieldCount() {
        return fieldCount;
    }
    
    public int getMaxFieldCount() {
        return maxFieldCount;
    }
}

