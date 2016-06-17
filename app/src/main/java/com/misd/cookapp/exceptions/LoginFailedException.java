package com.misd.cookapp.exceptions;

/*
 * @author Michael Landreh
 */
public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super("Login failed");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
