package com.misd.cookapp;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

public class User implements Serializable {

    private String lastname;
    private String firstname;
    private String street;
    private int postalCode;
    private String city;
    private char gender;
    private Calendar birthday;
    private String telephoneNumber;

    public User(String lastname, String firstname, String street, int postalCode, String city, char gender, Calendar birthday, String telephoneNumber) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.gender = gender;
        this.birthday = birthday;
        this.telephoneNumber = telephoneNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "User{Vorname: " + getFirstname() + ", Nachname: " + getLastname() + ", Geschlecht: " + (getGender() == 'w'? "weiblich" : "männlich") + ", Straße und Hausnummer: " + getStreet() + ", PLZ:" + getPostalCode() + ", Ort: "
                + getCity() + ", Telefonnummer: " + getTelephoneNumber();
    }
}
