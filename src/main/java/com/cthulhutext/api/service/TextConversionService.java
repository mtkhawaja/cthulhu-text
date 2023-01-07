package com.cthulhutext.api.service;


import com.cthulhutext.openapi.generated.model.CursedText;

public interface TextConversionService {
    CursedText convertToCursedText(String text);

    CursedText convertToCursedText(String text, int upperIntensity, int middleIntensity, int lowerIntensity);
}
