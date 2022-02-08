package com.github.afarentino.getfit.core;

/**
 * Custom Exception class to wrap lower-level exceptions
 */
public class ParseException extends Exception {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
