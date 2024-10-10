package com.skyapi.weatherforecast;

public class GeoLocationException extends Exception {
    public GeoLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeoLocationException(String message) {
        super(message);
    }
}
