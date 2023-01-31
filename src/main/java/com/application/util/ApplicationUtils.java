package com.application.util;

import java.time.LocalDate;
import java.time.Period;

public class ApplicationUtils {

    public static int getFullYears (LocalDate date) {
        LocalDate nowDate = LocalDate.now();
        Period period = Period.between(date, nowDate);
        return period.getYears();
    }
}
