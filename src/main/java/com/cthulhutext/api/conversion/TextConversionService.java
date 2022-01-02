package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;

public interface TextConversionService {
    CursedText convertToCursedText(String text);

    CursedText convertToCursedText(String text, int upperIntensity, int middleIntensity, int lowerIntensity);
}
