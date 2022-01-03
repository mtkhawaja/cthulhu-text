package com.cthulhutext.api.conversion;

public class ConversionTestUtils {
    private ConversionTestUtils() {
        throw new AssertionError(ConversionTestUtils.class + " should not be instantiated!");
    }

    public static int expectedCharacterCountForCursedText(String text, int upperIntensity, int middleIntensity, int lowerIntensity) {
        int perCharacterAdditions = upperIntensity + middleIntensity + lowerIntensity;
        return text.length() + (text.length() * perCharacterAdditions);
    }
}
