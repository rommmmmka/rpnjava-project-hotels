package com.kravets.hotels.rpnjava.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/Minsk"));
    }

    public static Date getDateTime() {
        return Date.from(getZonedDateTime().toInstant());
    }

    public static Date getDateTimeMinusDays(long days) {
        return Date.from(getZonedDateTime().minusDays(days).toInstant());
    }

    public static String convertZonedDateToString(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String convertDateToString(Date date) {
        ZonedDateTime zonedDateTime = convertDateToZonedDate(date);
        return convertZonedDateToString(zonedDateTime);
    }

    public static ZonedDateTime convertDateToZonedDate(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Minsk"));
    }

    public static Date convertZonedDateToDate(ZonedDateTime zonedDate) {
        return Date.from(zonedDate.toInstant());
    }

    public static String getStringDate() {
        return convertZonedDateToString(getZonedDateTime());
    }

    public static Date getDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(getStringDate());
    }

    public static String getStringShortDate() {
        return getZonedDateTime().format(DateTimeFormatter.ofPattern("d-M"));
    }
}
