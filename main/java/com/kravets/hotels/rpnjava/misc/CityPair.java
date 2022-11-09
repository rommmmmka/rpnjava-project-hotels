package com.kravets.hotels.rpnjava.misc;

public class CityPair {
    private final String city;

    private final boolean disabled;

    public CityPair(String city, boolean disabled) {
        this.city = city;
        this.disabled = disabled;
    }

    public String getCity() {
        return city;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
