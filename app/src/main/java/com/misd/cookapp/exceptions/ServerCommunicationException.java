package com.misd.cookapp.exceptions;

/**
 * Created by FH on 15.06.2016.
 */
public class ServerCommunicationException extends Exception {

    public ServerCommunicationException() {
        super("The communication to the server faild.");
    }

    public ServerCommunicationException(String message) {
        super(message);
    }
}
