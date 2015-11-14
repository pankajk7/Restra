package com.clairvoyant.Utils;

/**
 * Created by Clairvoyant on 04-11-2015.
 */
public enum ResponseCode {

    NOT_FOUND("Not Found", 404),
    UNAUTHORIZED("User Unauthorized", 401);

    private String stringValue;
    private int statusCode;

    private ResponseCode(String toString, int value) {
        stringValue = toString;
        statusCode = value;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
