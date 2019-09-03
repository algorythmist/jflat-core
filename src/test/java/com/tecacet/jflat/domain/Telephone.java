package com.tecacet.jflat.domain;

public class Telephone {

    private final String areaCode;
    private final String number;

    public Telephone(String phoneNumber) {
        String normalized = phoneNumber.replaceAll("\\s+", "").trim();
        if (normalized.length() != 10) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
        this.areaCode = normalized.substring(0,3);
        this.number = normalized.substring(3, 10);
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", areaCode, number);
    }
}
