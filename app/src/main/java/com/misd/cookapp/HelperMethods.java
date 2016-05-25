package com.misd.cookapp;

import java.util.Calendar;

public class HelperMethods {
    public static Calendar pasteCalendar(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day,hour,minute);
        return cal;
    }
}
