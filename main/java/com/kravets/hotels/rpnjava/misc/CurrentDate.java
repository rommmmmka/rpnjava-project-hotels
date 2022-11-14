package com.kravets.hotels.rpnjava.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CurrentDate {

    public static ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/Minsk"));
    }

    public static Date getDateTime() {
        return Date.from(getZonedDateTime().toInstant());
    }

    public static String convertToStringDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getStringDate() {
        return convertToStringDate(getZonedDateTime());
    }

    public static Date getDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(getStringDate());
    }

}
