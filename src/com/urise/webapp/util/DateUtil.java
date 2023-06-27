package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(9999, 12, 1);
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate parse(String date) {
        if(date.isEmpty() || "Сейчас".equals(date)) {
            return of(9999, Month.of(12));
        }
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (parsedDate.isAfter(LocalDate.now())) {
            return of(9999, Month.of(12));
        }
        return parsedDate;
    }
}
