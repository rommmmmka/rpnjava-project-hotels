package com.kravets.hotels.rpnjava.misc;

import java.util.List;

public class CitiesList {
    private static final List<CityPair> citiesList = List.of(
            new CityPair("Мінск", false),
            new CityPair("Брэст", false),
            new CityPair("Віцебск", false),
            new CityPair("Гомель", false),
            new CityPair("Гродна", false),
            new CityPair("Магілёў", false),
            new CityPair("---Брэсцкая вобласць---", true),
            new CityPair("Пінск", false)
    );

    public static List<CityPair> getCitiesList() {
        return citiesList;
    }
}
