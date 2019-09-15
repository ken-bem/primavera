package com.kenbem.primavera.utils.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("");

    /*
    * Convert Calendar to LocalDate
    */
    public static LocalDate convertCalendarDateToLocalDate(Calendar calendar){
        return calendar
                .getTime()
                .toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime today(){
        return LocalDateTime.now();
    }

    public static String todayStr(){
        return sdf.format(today());
    }

    public static String formattedDate(Date date) {
        return date != null ? sdf.format(date) : todayStr();
    }



}
