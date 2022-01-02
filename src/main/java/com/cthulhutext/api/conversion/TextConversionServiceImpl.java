package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
@PropertySource("classpath:/static/diacritics.properties")
public class TextConversionServiceImpl implements TextConversionService {

    private static final int DEFAULT_CHARACTER_ADDITION_ROUNDS = 30;
    private final int diacriticTypeCount;
    private final char[] upperDiacritics;
    private final char[] middleDiacritics;
    private final char[] lowerDiacritics;
    private final List<char[]> diacritics;

    public TextConversionServiceImpl(@Value("${combining.diacritical.marks.upper}") char[] upperDiacritics,
                                     @Value("${combining.diacritical.marks.middle}") char[] middleDiacritics,
                                     @Value("${combining.diacritical.marks.lower}") char[] lowerDiacritics) {
        this.upperDiacritics = upperDiacritics;
        this.middleDiacritics = middleDiacritics;
        this.lowerDiacritics = lowerDiacritics;
        this.diacritics = new ArrayList<>();
        this.diacritics.add(this.upperDiacritics);
        this.diacritics.add(this.middleDiacritics);
        this.diacritics.add(this.lowerDiacritics);
        this.diacriticTypeCount = diacritics.size();
    }


    @Override
    public CursedText convertToCursedText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        int textLength = text.length();
        char[] diacriticTypeReference;
        for (int charIndex = 0; charIndex < textLength; charIndex++) {
            stringBuilder.append(text.charAt(charIndex));
            for (int rounds = 0; rounds < DEFAULT_CHARACTER_ADDITION_ROUNDS; rounds++) {
                diacriticTypeReference = selectDiacriticType();
                stringBuilder.append(getRandomDiacriticMark(diacriticTypeReference));
            }
        }
        return new CursedText(stringBuilder.toString());
    }

    @Override
    public CursedText convertToCursedText(String text, int upperIntensity, int middleIntensity, int lowerIntensity) {
        StringBuilder stringBuilder = new StringBuilder();
        int textLength = text.length();
        for (int charIndex = 0; charIndex < textLength; charIndex++) {
            stringBuilder.append(text.charAt(charIndex));
            stringBuilder.append(getCharactersDiacriticType(this.upperDiacritics, upperIntensity));
            stringBuilder.append(getCharactersDiacriticType(this.middleDiacritics, middleIntensity));
            stringBuilder.append(getCharactersDiacriticType(this.lowerDiacritics, lowerIntensity));
        }
        return new CursedText(stringBuilder.toString());
    }

    private String getCharactersDiacriticType(char[] diacriticMarks, int intensity) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int round = 0; round < intensity; round++) {
            stringBuilder.append(getRandomDiacriticMark(diacriticMarks));
        }
        return stringBuilder.toString();
    }

    private char getRandomDiacriticMark(char[] diacriticTypeReference) {
        return diacriticTypeReference[getRandomInteger(diacriticTypeReference.length)];
    }

    private char[] selectDiacriticType() {
        int choice = getRandomInteger(this.diacriticTypeCount);
        return diacritics.get(choice);
    }

    private int getRandomInteger(int upperBound) {
        return ThreadLocalRandom.current().nextInt(0, upperBound);
    }


}
