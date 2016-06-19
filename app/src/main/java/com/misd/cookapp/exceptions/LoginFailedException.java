package com.misd.cookapp.exceptions;

/**
 * This Exception is thrown if the user login fails
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
