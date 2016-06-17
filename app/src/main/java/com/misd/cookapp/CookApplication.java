package com.misd.cookapp;

import android.app.Application;

import com.misd.cookapp.interfaces.IServer;

/*
 * @author Ines Müller
 */
public class CookApplication extends Application {
    private int sessionId;
    private User loggedInUser;
    private Event currentEvent;
    private IServer server;
    private static CookApplication cookApplication;

    public  CookApplication () {
        cookApplication = this;
        this.server = new Server();
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public IServer getServer() {
        return server;
    }

    public static CookApplication getCookApplication() {
        return cookApplication;
    }
}
