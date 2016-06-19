package com.misd.cookapp.exceptions;

/**
 * This Exception is used to report an error in the communication with the server itself.
 * Especially, if the server does not respond or answers with a unexpected null-object.
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
