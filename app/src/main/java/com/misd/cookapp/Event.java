package com.misd.cookapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event {
    private int IconId;
    private String meal;
    private String location;
    private Calendar date;

    public Event(int IconId, String meal, String location, Calendar date){
        super();
        this.IconId = IconId;
        this.meal = meal;
        this.location = location;
        this.date = date;
    }


    public int getIconId() {
        return IconId;
    }

    public String getMeal() {
        return meal;
    }

    public String getLocation(){
        return location;
    }

    // public Calendar getDate() { return date; }

    public String getDateAsString() {return new SimpleDateFormat("dd.MM.yyyy").format(date.getTime());}

    public String getTimeAsString() {return new SimpleDateFormat("hh:mm").format(date.getTime());}
}
