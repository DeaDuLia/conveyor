package com.application.util;

import java.time.LocalDate;
import java.time.Period;


public class ApplicationUtils {

    public static int getFullYears (LocalDate date) {
        LocalDate nowDate = LocalDate.now();
        Period period = Period.between(date, nowDate);
        return Math.abs(period.getYears());
    }
    public static int getFullDaysBetweenDates (LocalDate start, LocalDate end) {
        int daysInStartMonth = start.getMonth().maxLength() - start.getDayOfMonth();
        int daysInEndMonth = end.getDayOfMonth();
        return daysInStartMonth + daysInEndMonth;
    }
}
