package com.example.meteorCleaning.util;

import java.time.LocalDateTime;

public class TimeUtil {

    public static String humanDate(LocalDateTime date){
        return date.getMonth() + " " + date.getDayOfMonth() + " " + date.getYear();
    }

    public static String humanTime(LocalDateTime date){
        return date.getHour() + ":00";
    }

}
