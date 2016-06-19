package com.misd.cookapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * An Event contains Meal and all Event attributes.
 * @author Michael Landreh, Ines Mueller
 */
public class Event implements Serializable{
    // private int IconId;
    private int eventId;
    private String eventDescription;
    private Meal eventMeal;
    private int eventMinAge;
    private int eventMaxAge;
    private char eventGender;
    private String eventStreet;
    private int eventPostal;
    private String eventCity;
    private String eventOwner;
    private Calendar eventDate;

    // private String address;

    public Event(int eventId, String eventDescription, Meal eventMeal, int eventMinAge, int eventMaxAge,
                 char eventGender, String eventStreet, int eventPostal, String eventCity, String eventOwner, Calendar eventDate) {

        this.eventId = eventId;
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


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

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

    public String getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(String eventOwner) {
        this.eventOwner = eventOwner;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDateAsString() {
        return new SimpleDateFormat("dd. MMM yyyy").format(eventDate.getTime());
    }

    public String getEventTimeAsString() {return new SimpleDateFormat("HH:mm").format(eventDate.getTime());}


}
