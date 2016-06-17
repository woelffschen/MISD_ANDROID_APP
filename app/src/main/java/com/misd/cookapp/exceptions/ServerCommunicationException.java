package com.misd.cookapp.exceptions;

/*
 * @author Michael Landreh
 */
public class ServerCommunicationException extends Exception {

    public ServerCommunicationException() {
        super("The communication to the server faild.");
    }

    public ServerCommunicationException(String message) {
        super(message);
    }
}
