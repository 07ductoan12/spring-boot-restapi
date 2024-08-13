package com.toan.unit_conversion_api;

/** unitConverter */
public class UnitConverter {
    static void convert(ConversionDetails details) throws UnitConversionException {
        String fromUnit = details.getFromUnit();
        String toUnit = details.getToUnit();

        if (fromUnit.equals("km") && toUnit.equals("mile")) {
            LengthConverter.kilometer2Mile(details);
        } else if (fromUnit.equals("mile") && toUnit.equals("km")) {
            LengthConverter.kilometer2Mile(details);
        } else {
            throw new UnitConversionException("Invalid From and To Units");
        }
    }
}
