package com.misd.cookapp;

import android.util.Log;

import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.exceptions.ServerCommunicationException;
import com.misd.cookapp.helpers.HelperMethods;
import com.misd.cookapp.interfaces.IServer;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class implements the IServer Interface.
 * @author Ines Müller
 */
public class Server implements IServer {
    private static final String TAG = "WebserviceClient";

    /*
     * UserInterface
     */

    // liefert sessionId - benötigt Parameter
    public int register(String mailAddress, String lastname, String firstname, String street, int postalCode, String city,
                        int unixDateOfBirth, String telephoneNumber, char gender) throws Exception {
        int genderAsInt = Integer.valueOf(gender);
        SoapObject resultRegister = HelperMethods.executeSessionSoapAction("registerUser", mailAddress, lastname, firstname, street, postalCode, city, unixDateOfBirth, telephoneNumber, genderAsInt);
        int returnCode = Integer.valueOf(resultRegister.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultRegister.getPrimitivePropertySafelyAsString("sessionId"));
        } else {
            String errorMessage = resultRegister.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    // liefert sessionId - benötigt Emailaddresse
    public int login(String mailAddress) throws LoginFailedException, SoapFault {
        SoapObject resultLogin = HelperMethods.executeSessionSoapAction("loginUser", mailAddress);

        int returnCode = Integer.valueOf(resultLogin.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultLogin.getPrimitivePropertySafelyAsString("sessionId"));
        } else {
            String errorMessage = resultLogin.getPrimitivePropertySafelyAsString("message");
            throw new LoginFailedException(errorMessage);
        }
    }

    // keine Rückgabe notwendig - benötigt sessionId
    public void logout(int sessionId) throws Exception {
        SoapObject resultLogout = HelperMethods.executeSessionSoapAction("logout", sessionId);
        int returnCode = Integer.valueOf(resultLogout.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode != 0) {
            String errorMessage = resultLogout.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    //getPrivateData

    public User getUserInfo(String mailAddress) throws Exception {
        SoapObject resultGetPublicData = HelperMethods.executeSessionSoapAction("getPublicData", mailAddress);

        /*lastname, firstname;*/
        int returnCode = Integer.valueOf(resultGetPublicData.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode != 0) {
            String errorMessage = resultGetPublicData.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        } else {
            String firstname = resultGetPublicData.getPrimitivePropertySafelyAsString("firstname");
            String lastname = resultGetPublicData.getPrimitivePropertySafelyAsString("lastname");
            int postalCode = Integer.valueOf(resultGetPublicData.getPrimitivePropertySafelyAsString("postalCode"));
            String street = resultGetPublicData.getPrimitivePropertySafelyAsString("street");
            String city = resultGetPublicData.getPrimitivePropertySafelyAsString("city");
            char gender = resultGetPublicData.getPrimitivePropertySafelyAsString("gender").charAt(0);
            int age = Integer.valueOf(resultGetPublicData.getPrimitivePropertySafelyAsString("age"));
            String telephoneNumber = resultGetPublicData.getPrimitivePropertySafelyAsString("telephoneNumber");

            return new User(mailAddress, lastname, firstname, street, postalCode, city, gender, age, telephoneNumber);
        }
    }

    // keine Rückgabe notwendig - benötigt userId
    // public static void delete(int userId) throws Exception {}


    /*
     * Attendance Interface
     * statusCodes:
     * 0 = eventOwner
     * 1 = cancel
     * 2 = confirm
     * 3 = request
     * 4 = reject
     */
    //

    // liefert status - benötigt sessionId, , eventId, userId
    public int cancel(int sessionId, int eventId, String email) throws Exception {
        SoapObject resultCancel = HelperMethods.executeAttendanceSoapAction("cancelAttendance", sessionId, eventId, email);
        int returnCode = Integer.valueOf(resultCancel.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultCancel.getPrimitivePropertySafelyAsString("status"));
        } else {
            String errorMessage = resultCancel.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    // liefert status - benötigt sessionId, eventId, userId
    public int request(int sessionId, int eventId, String email) throws Exception {
        SoapObject resultRequest = HelperMethods.executeAttendanceSoapAction("requestAttendance", sessionId, eventId, email);
        int returnCode = Integer.valueOf(resultRequest.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultRequest.getPrimitivePropertySafelyAsString("status"));
        } else {
            String errorMessage = resultRequest.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    // liefert status - benötigt sessionId, eventId, userId
    public int confirm(int sessionId, int eventId, String email) throws Exception {
        SoapObject resultConfirm = HelperMethods.executeAttendanceSoapAction("confirmAttendance", sessionId, eventId, email);
        int returnCode = Integer.valueOf(resultConfirm.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultConfirm.getPrimitivePropertySafelyAsString("status"));
        } else {
            String errorMessage = resultConfirm.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    // liefert status - benötigt sessionId, eventId, userId
    public int reject(int sessionId, int eventId, String email) throws Exception {
        SoapObject resultReject = HelperMethods.executeAttendanceSoapAction("rejectAttendance", sessionId, eventId, email);
        int returnCode = Integer.valueOf(resultReject.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            return Integer.valueOf(resultReject.getPrimitivePropertySafelyAsString("status"));
        } else {
            String errorMessage = resultReject.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        }
    }



    /*
     * Event Interface
     */

    // keine Rückgabe - benötigt Paramter inkl. sessionId, user Id (eventId wird bei Liste geliefert)
    public int create(int sessionId, int min, int max, String street, int plz, String city,
                      String comments, char gender, int dateTime, String eo, String name, boolean lactose, boolean gluten,
                      boolean fructose, boolean sorbit, boolean vega, boolean vegee) throws Exception {
        SoapObject resultCreate = HelperMethods.executeEventSoapAction("createEvent", sessionId, min, max, street, plz, city, comments, (int) gender, dateTime, eo, name, lactose, gluten, fructose, sorbit, vega, vegee);
        int returnCode = Integer.valueOf(resultCreate.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode != 0) {
            String errorMessage = resultCreate.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        } else {
            return Integer.valueOf(resultCreate.getPrimitivePropertySafelyAsString("eventId"));
        }
    }

    // keine Rückgabe - benötigt eventId, userId
    public void delete(int eventId, String email) throws Exception {
        SoapObject resultDelete = HelperMethods.executeSessionSoapAction("deleteEvent", eventId, email);
        int returnCode = Integer.valueOf(resultDelete.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode != 0) {
            String errorMessage = resultDelete.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage);// TODO spezielle Exception für Fehlercodes erstellen
        }
    }

    // einzelne Eventrückgabe - benötigt eventId
    public Event getOneEvent(int eventId) throws Exception {
        SoapObject resultgetOneEvent = HelperMethods.executeEventSoapAction("getEvent", eventId);
        int returnCode = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            //int menueId = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("menueId"));
            int minAge = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("minAge"));
            int maxAge = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("maxAge"));
            char gender = resultgetOneEvent.getPrimitivePropertySafelyAsString("gender").charAt(0);
            String street = ""; //resultgetOneEvent.getPrimitivePropertySafelyAsString("street");
            int postalCode = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("eventPostalCode"));
            String city = resultgetOneEvent.getPrimitivePropertySafelyAsString("eventCity");
            String description = resultgetOneEvent.getPrimitivePropertySafelyAsString("comments");
            int dateOfEvent = Integer.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("dateTime"));
            String eventOwner = resultgetOneEvent.getPrimitivePropertySafelyAsString("eventOwner");
            String eventName = resultgetOneEvent.getPrimitivePropertySafelyAsString("name");
            boolean lactose = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("lactose"));
            boolean gluten = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("gluten"));
            boolean fructose = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("fructose"));
            boolean sorbit = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("sorbit"));
            boolean vegan = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("vega"));
            boolean vegetarian = Boolean.valueOf(resultgetOneEvent.getPrimitivePropertySafelyAsString("vegee"));
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(dateOfEvent * 1000);

            return new Event(eventId, description, new Meal(eventName, lactose, gluten, fructose, sorbit, vegan, vegetarian), minAge, maxAge, gender, street, postalCode, city, eventOwner, cal);

        } else {
            String errorMessage = resultgetOneEvent.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        }

    }

    // Array von Events - benötigt sessionId
    public List<Event> getListOfEvents(int sessionId) throws Exception {
        List<Event> eventList = new ArrayList<Event>();

        SoapObject resultGetListOfEvents = HelperMethods.executeEventSoapAction("getEvents", sessionId);
        int returnCode = Integer.valueOf(resultGetListOfEvents.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode == 0) {
            for (int i = 1; i < resultGetListOfEvents.getPropertyCount(); i++) {
                SoapObject resultgetOneEvent = (SoapObject) resultGetListOfEvents.getProperty(i);

                int eventId = Integer.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("eventId")).getValue().toString());
                int minAge = Integer.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("minAge")).getValue().toString());
                int maxAge = Integer.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("maxAge")).getValue().toString());
                char gender = ((SoapPrimitive) resultgetOneEvent.getProperty("gender")).getValue().toString().charAt(0);
                String street = ((SoapPrimitive) resultgetOneEvent.getProperty("eventStreet")).getValue().toString();
                int postalCode = Integer.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("eventPostalCode")).getValue().toString());
                String city = ((SoapPrimitive) resultgetOneEvent.getProperty("eventCity")).getValue().toString();
                String description = ((SoapPrimitive) resultgetOneEvent.getProperty("comments")).getValue().toString();
                Integer dateOfEvent = Integer.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("dateTime")).getValue().toString());
                String eventOwner = ((SoapPrimitive) resultgetOneEvent.getProperty("eventOwner")).getValue().toString();
                String eventName = ((SoapPrimitive) resultgetOneEvent.getProperty("name")).getValue().toString();
                boolean lactose = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("lactose")).getValue().toString());
                boolean gluten = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("gluten")).getValue().toString());
                boolean fructose = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("fructose")).getValue().toString());
                boolean sorbit = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("sorbit")).getValue().toString());
                boolean vegan = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("vega")).getValue().toString());
                boolean vegetarian = Boolean.valueOf(((SoapPrimitive) resultgetOneEvent.getProperty("vegee")).getValue().toString());
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(dateOfEvent * 1000);

                eventList.add(new Event(eventId, description, new Meal(eventName, lactose, gluten, fructose, sorbit, vegan, vegetarian), minAge, maxAge, gender, street, postalCode, city, eventOwner, cal));
            }

        } else {
            String errorMessage = resultGetListOfEvents.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        }
        return eventList;
    }

    public int getEventStatus(String email, int eventId) throws SoapFault, Exception {
        SoapObject resultGetPublicData = HelperMethods.executeSessionSoapAction("getPublicUserData", email, eventId);

        int returnCode = Integer.valueOf(resultGetPublicData.getPrimitivePropertySafelyAsString("returnCode"));
        if (returnCode != 0) {
            String errorMessage = resultGetPublicData.getPrimitivePropertySafelyAsString("message");
            throw new Exception(errorMessage); // TODO spezielle Exception für Fehlercodes erstellen
        } else {
            return Integer.valueOf(resultGetPublicData.getPrimitivePropertySafelyAsString("attendanceStatus"));
        }
    }

    public void filterCity(int sessionId, String city) throws Exception {
    }


}
