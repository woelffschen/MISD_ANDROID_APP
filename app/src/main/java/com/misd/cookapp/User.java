package com.misd.cookapp;

import java.io.Serializable;

public class User implements Serializable {
    // TODO int ID hinzufügen
    // TODO Alle nötigen Informationen vom user für den Server Sammeln: age, gender, telefonnummer
    // TODO sessionId speichern und mitschicken

    private String lastname;
    private String firstname;
    private String street;
    private int postalCode;
    private String city;
    private char gender;
    private int age;
    private String telephoneNumber;

    public User(String lastname, String firstname, String street, int postalCode, String city, char gender, int age, String telephoneNumber) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.gender = gender;
        this.age = age;
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

    public int getPostelCode() {
        return postalCode;
    }

    public void setPostelCode(int postelCode) {
        this.postalCode = postelCode;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
