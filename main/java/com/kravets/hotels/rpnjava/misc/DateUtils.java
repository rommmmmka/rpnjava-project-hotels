package com.kravets.hotels.rpnjava.misc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(ZoneId.of("Europe/Minsk"));
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("Europe/Minsk"));
    }

    public static String convertDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String convertDateToShortString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("d-M"));
    }

    public static String convertDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
