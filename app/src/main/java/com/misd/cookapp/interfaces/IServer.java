package com.misd.cookapp.interfaces;

import com.misd.cookapp.Event;
import com.misd.cookapp.User;
import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.helpers.HelperMethods;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

/**
 * Created by FH on 16.06.2016.
 */
public interface IServer {
    public int register(String mailAddress, String lastname, String firstname, String street, int postalCode, String city,
                        int unixDateOfBirth, String telephoneNumber, char gender) throws Exception;

    public int login(String mailAddress) throws LoginFailedException, SoapFault;

    public void logout(int sessionId) throws Exception;

    public User getUserInfo(String mailAddress) throws Exception;

    public int cancel(int sessionId, int attendanceId, int eventId) throws Exception;

    public int request(int sessionId, int eventId, String email) throws Exception;

    public int confirm(int sessionId, int attendanceId, int eventId, int userId) throws Exception;

    public int reject(int sessionId, int attendanceId, int eventId) throws Exception;

    public int create(int sessionId, int min, int max, String street, int plz, String city,
                      String comments, char gender, int dateTime, String eo, String name, boolean lactose, boolean gluten,
                      boolean fructose, boolean sorbit, boolean vega, boolean vegee) throws Exception;

    public void delete(int eventId, String email) throws Exception;

    public void filterCity(int sessionId, String city) throws Exception;

    public Event getOneEvent(int eventId) throws Exception;

    public List<Event> getListOfEvents(int sessionId) throws Exception;

    public int getEventStatus(String email, int eventId) throws SoapFault, Exception;
}
