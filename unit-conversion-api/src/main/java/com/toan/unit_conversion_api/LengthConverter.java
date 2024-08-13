package com.toan.unit_conversion_api;

/** LengthConverter */
public class LengthConverter {

    static void kilometer2Mile(ConversionDetails details) {
        float km = details.getFromValue();
        float mile = km * 0.621371f;
        details.setToValue(mile);
    }

    static void mile2Kilometer(ConversionDetails details) {
        float mile = details.getFromValue();
        float km = mile * 1.60934f;
        details.setToValue(km);
    }
}
