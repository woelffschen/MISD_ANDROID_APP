package com.misd.cookapp.exceptions;

/**
 * Created by FH on 15.06.2016.
 */
public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super("Login failed");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
