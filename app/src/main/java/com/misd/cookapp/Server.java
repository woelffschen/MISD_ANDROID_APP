package com.misd.cookapp;

import com.misd.cookapp.helpers.HelperMethods;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;

/**
 * Created by Ines on 14.06.2016.
 */
public class Server {

    /*
     * UserInterface
     */

    // liefert UserId
    public static int register(String lastname, String firstname, String street, int postalCode, String city,
                                int age, String telephoneNumber, char gender) throws Exception {
        SoapObject resultRegister = HelperMethods.executeSessionSoapAction("registerUser", lastname, firstname, street, postalCode, city, age, telephoneNumber, (int)gender);
        int returnCode = Integer.valueOf(resultRegister.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode == 0) {
            return Integer.valueOf(resultRegister.getPrimitivePropertySafelyAsString("userId"));
        }else {
            String errorMessage = resultRegister.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // liefert SessionId
    public static int login(int userId) throws Exception {
        SoapObject resultLogin = HelperMethods.executeSessionSoapAction("loginUser", userId);
        String strReturnCode = resultLogin.getPrimitivePropertySafelyAsString("returnCode");
        int returnCode = Integer.valueOf(strReturnCode);
        if(returnCode == 0) {
            return Integer.valueOf(resultLogin.getPrimitivePropertySafelyAsString("sessionId"));
        }else {
            String errorMessage = resultLogin.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // keine Rückgabe notwendig
    public static void logout(int sessionId) throws Exception {
        SoapObject resultLogout = HelperMethods.executeSessionSoapAction("logout", sessionId);
        int returnCode = Integer.valueOf(resultLogout.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode != 0) {
            String errorMessage = resultLogout.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // im Client bisher nicht vorgesehen
    // public static void delete(int userId) throws Exception {}



    /*
     * Attendance Interface
     */
    //

    public static void cancel(int sessionId, int attendanceId, int eventId) throws Exception {}

    public static void request(int sessionId, int eventId, int userId) throws Exception {}

    public static void confirm(int sessionId, int attendanceId, int eventId, int userId) throws Exception {}

    public static void reject(int sessionId, int attendanceId, int eventId) throws Exception {}



    /*
     * Event Interface
     */

    public static void create(int sessionId, int userId, int min, int max, String street, int plz, String city,
                                   String comments, char gender, Calendar dateTime, int eo, String name, boolean lactose, boolean gluten,
                                   boolean fructose, boolean sorbit, boolean vega, boolean vegee) throws Exception {}

    public static void delete(int eventId, int userId) throws Exception {}

    public static void filterCity(int sessionId, String city) throws Exception {}

}
