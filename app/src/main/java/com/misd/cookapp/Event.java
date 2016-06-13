package com.misd.cookapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event implements Serializable{
    // private int IconId;
    // private int eventId;
    private String eventDescription;
    private Meal eventMeal;
    private int eventMinAge;
    private int eventMaxAge;
    private char eventGender;
    private String eventStreet;
    private int eventPostal;
    private String eventCity;
    private User eventOwner; // TODO laut Server ändern in: int eventOwner
    private Calendar eventDate;

    // private String address;

    public Event(String eventDescription, Meal eventMeal, int eventMinAge, int eventMaxAge,
                 char eventGender, String eventStreet, int eventPostal, String eventCity, User eventOwner, Calendar eventDate) {

        // this.IconId = R.drawable.pic1;
        this.eventDescription = eventDescription;
        this.eventMeal = eventMeal;
        this.eventMinAge = eventMinAge;
        this.eventMaxAge = eventMaxAge;
        this.eventGender = eventGender;
        this.eventStreet = eventStreet;
        this.eventPostal = eventPostal;
        this.eventCity = eventCity;
        this.eventOwner = eventOwner;
        this.eventDate = eventDate;

    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    /* public int getIconId() {
        return IconId;
    }*/

    /* public void setIconId(int iconId) {
        IconId = iconId;
    }*/

    public Meal getEventMeal() {
        return eventMeal;
    }

    public void setEventMeal(Meal eventMeal) {
        this.eventMeal = eventMeal;
    }

    public int getEventMinAge() {
        return eventMinAge;
    }

    public void setEventMinAge(int eventMinAge) {
        this.eventMinAge = eventMinAge;
    }

    public int getEventMaxAge() {
        return eventMaxAge;
    }

    public void setEventMaxAge(int eventMaxAge) {
        this.eventMaxAge = eventMaxAge;
    }

    public char getEventGender() {
        return eventGender;
    }

    public void setEventGender(char eventGender) {
        this.eventGender = eventGender;
    }

    public String getEventStreet() {
        return eventStreet;
    }

    public void setEventStreet(String eventStreet) {
        this.eventStreet = eventStreet;
    }

    public int getEventPostal() {
        return eventPostal;
    }

    public void setEventPostal(int eventPostal) {
        this.eventPostal = eventPostal;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public User getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(User eventOwner) {
        this.eventOwner = eventOwner;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDateAsString() {
        /*String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;*/
        return new SimpleDateFormat("dd. MMM yyyy").format(eventDate.getTime());
    }

    public String getEventTimeAsString() {return new SimpleDateFormat("hh:mm").format(eventDate.getTime());}


}
