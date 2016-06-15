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

    // liefert userId und sessionId - benötigt Parameter
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

    // liefert sessionId - benötigt userId
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

    // keine Rückgabe notwendig - benötigt sessionId
    public static void logout(int sessionId) throws Exception {
        SoapObject resultLogout = HelperMethods.executeSessionSoapAction("logout", sessionId);
        int returnCode = Integer.valueOf(resultLogout.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode != 0) {
            String errorMessage = resultLogout.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // keine Rückgabe notwendig - benötigt userId
    // public static void delete(int userId) throws Exception {}

    // public static void getPublicUserData(int userId) throws Exception {}


    /*
     * Attendance Interface
     * statusCodes:
     * 1 = cancel
     * 2 = confirm
     * 3 = request
     * 4 = reject
     */
    //

    // liefert status - benötigt sessionId, , attendanceId, eventId
    public static int cancel(int sessionId, int attendanceId, int eventId) throws Exception {
        SoapObject resultCancel = HelperMethods.executeSessionSoapAction("cancelAttendance", sessionId, attendanceId, eventId);
        int returnCode = Integer.valueOf(resultCancel.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode == 0) {
            return Integer.valueOf(resultCancel.getPrimitivePropertySafelyAsString("status"));
        }else {
            String errorMessage = resultCancel.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // liefert status und attendanceId - benötigt sessionId, eventId, userId
    public static int request(int sessionId, int eventId, int userId) throws Exception {
        SoapObject resultRequest = HelperMethods.executeSessionSoapAction("requestAttendance", sessionId, eventId, userId);
        int returnCode = Integer.valueOf(resultRequest.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode == 0) {
            return Integer.valueOf(resultRequest.getPrimitivePropertySafelyAsString("status"));
        }else {
            String errorMessage = resultRequest.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // liefert status - benötigt sessionId, attendanceId, eventId, userId
    public static int confirm(int sessionId, int attendanceId, int eventId, int userId) throws Exception {
        SoapObject resultConfirm = HelperMethods.executeSessionSoapAction("confirmAttendance", sessionId, attendanceId, eventId, userId);
        int returnCode = Integer.valueOf(resultConfirm.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode == 0) {
            return Integer.valueOf(resultConfirm.getPrimitivePropertySafelyAsString("status"));
        }else {
            String errorMessage = resultConfirm.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // liefert status - benötigt sessionId, attendanceId, eventId
    public static int reject(int sessionId, int attendanceId, int eventId) throws Exception {
        SoapObject resultReject = HelperMethods.executeSessionSoapAction("rejectAttendance", sessionId, attendanceId, eventId);
        int returnCode = Integer.valueOf(resultReject.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode == 0) {
            return Integer.valueOf(resultReject.getPrimitivePropertySafelyAsString("status"));
        }else {
            String errorMessage = resultReject.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }



    /*
     * Event Interface
     */

    // keine Rückgabe - benötigt Paramter inkl. sessionId, user Id (eventId wird bei Liste geliefert)
    public static void create(int sessionId, int userId, int min, int max, String street, int plz, String city,
                                   String comments, char gender, Calendar dateTime, int eo, String name, boolean lactose, boolean gluten,
                                   boolean fructose, boolean sorbit, boolean vega, boolean vegee) throws Exception {
        SoapObject resultCreate = HelperMethods.executeSessionSoapAction("createEvent", sessionId, userId, min, max, street, plz, city, comments, (int)gender, dateTime, eo, name, lactose, gluten, fructose, sorbit, vega, vegee);
        int returnCode = Integer.valueOf(resultCreate.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode != 0) {
            String errorMessage = resultCreate.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // keine Rückgabe - benötigt eventId, userId
    public static void delete(int eventId, int userId) throws Exception {
        SoapObject resultDelete = HelperMethods.executeSessionSoapAction("deleteEvent", eventId, userId);
        int returnCode = Integer.valueOf(resultDelete.getPrimitivePropertySafelyAsString("returnCode"));
        if(returnCode != 0) {
            String errorMessage = resultDelete.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);
        }
    }

    // ??
    public static void filterCity(int sessionId, String city) throws Exception {}

    // weitere Methoden für Anzeige "Meine Events" notwendig ??

}
