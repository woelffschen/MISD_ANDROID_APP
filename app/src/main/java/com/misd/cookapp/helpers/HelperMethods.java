package com.misd.cookapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;
import java.util.List;

/*
 * @author Michael Landreh
 */
public class HelperMethods {
    public static final String PREFS_USER_DATA = "user_data";
    public static final String PREFS_SESSION_DATA = "session_data";

    public static Calendar pasteCalendar(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day,hour,minute);
        return cal;
    }

    public static Calendar pasteCalendar(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        return cal;
    }

    public static void setPreferenceString(Context context, String prefsName, String key, String value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }

    public static String getPreferenceString(Context context, String prefsName, String key) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        String output = settings.getString(key, null);
        return output;
    }

    public static void setPreferenceInt(Context context, String prefsName, String key, int value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);

        // Commit the edits!
        editor.commit();
    }

    public static int getPreferenceInt(Context context, String prefsName, String key) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        int output = settings.getInt(key,-1);
        return output;
    }

    public static void setPreferenceObject(Context context, String prefsName, String key, Object value) {
        SharedPreferences  mPrefs = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String jsonValue = gson.toJson(value);
        prefsEditor.putString(key, jsonValue);
        prefsEditor.commit();
    }

    public static Object getPreferenceObject(Context context, String prefsName, Class expectedClassType, String key) {
        SharedPreferences  mPrefs = context.getSharedPreferences(prefsName, 0);
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String json = mPrefs.getString(key, "");
        return gson.fromJson(json, expectedClassType);
    }

    public static boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(Exception e) {
            return false;
        }
    }


    /*
     * @author Ines Müller - Kommunikation mit dem Server
     */
    private static final String NAMESPACE = "http://onlineService/";

    // localen Port bzw die vom Cluster ansprechen
    // Deployment Server Sylvia
    //private static final String SESSION_URL = "http://10.70.16.37:8080/software-engineering-wildfly-archetype-ejb-1.0.0/UserInterface";
    //private static final String EVENT_URL ="http://10.70.16.37:8080/software-engineering-wildfly-archetype-ejb-1.0.0/EventInterface";
    //private static final String ATTENDANCE_URL="http://10.70.16.37:8080/software-engineering-wildfly-archetype-ejb-1.0.0/AttendanceInterface";

    //Apptest: Server Ines
    private static final String SESSION_URL = "http://192.168.178.53:8080/software-engineering-wildfly-archetype-ejb-1.0.0/UserInterface";
    private static final String EVENT_URL ="http://192.168.178.53:8080/software-engineering-wildfly-archetype-ejb-1.0.0/EventInterface";
    private static final String ATTENDANCE_URL="http://192.168.178.53:8080/software-engineering-wildfly-archetype-ejb-1.0.0/AttendanceInterface";


    public static SoapObject executeSessionSoapAction( String methodName, Object... args) throws SoapFault {
        return executeSoapAction(SESSION_URL, methodName, args);
    }
    public static SoapObject executeEventSoapAction( String methodName, Object... args) throws SoapFault {
        return executeSoapAction(EVENT_URL, methodName, args);
    }
    public static SoapObject executeAttendanceSoapAction( String methodName, Object... args) throws SoapFault {
        return executeSoapAction(ATTENDANCE_URL, methodName, args);
    }

    private static SoapObject executeSoapAction(String url, String methodName, Object... args) throws SoapFault {

        Object result = null;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        for (int i=0; i<args.length; i++) {
            request.addProperty("arg" + i, args[i]);
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

        try {
            List<HeaderProperty> reqHeaders = null;
            List resp = androidHttpTransport.call("", envelope, reqHeaders);
            result = envelope.getResponse();

            if (result instanceof SoapFault) {
                throw (SoapFault) result;
            }
        }
        catch (SoapFault e) {
            String msg = e.getMessage();

            String t = msg;
        }
        catch (Exception e) {
            //Log.i("Server: " , e.getMessage());
            String msg = e.getMessage();

            String t = msg;
        }

        return (SoapObject) result;
    }

}
