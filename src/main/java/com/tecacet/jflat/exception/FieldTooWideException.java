package com.tecacet.jflat.exception;

public class FieldTooWideException extends LineMergerException {

    private final String fieldValue;
    private final int maxFieldWidth;
    
    public FieldTooWideException(String fieldValue, int maxFieldWidth) {
        super();
        this.fieldValue = fieldValue;
        this.maxFieldWidth = maxFieldWidth;
    }
    
    @Override
    public String getMessage(){
        String messageFormat = "Value '%s' is too wide.  Actual width: %d, Maximum width: %d.";
        String message = String.format( messageFormat, fieldValue, fieldValue.length(), maxFieldWidth);
        return message;
    }
    
    public String getFieldValue() {
        return fieldValue;
    }
    
    public int getMaxFieldWidth() {
        return maxFieldWidth;
    }
}
