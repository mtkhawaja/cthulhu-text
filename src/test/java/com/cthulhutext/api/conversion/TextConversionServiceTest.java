package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TextConversionServiceTest {
    private final String baseText = "In his house at R'lyeh dead Cthulhu waits dreaming";
    @Autowired
    private TextConversionService textConversionService;

    @DisplayName("Should create CursedText When given regular text String")
    @Test
    void shouldCreateCursedTextWhenGivenRegularText() {
        CursedText cursedText = textConversionService.convertToCursedText(baseText);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(baseText.length());
    }

    @DisplayName("Should create CursedText When given text and intensity parameters")
    @Test
    void shouldCreateCursedTextWhenGivenTextAndIntensityParameters() {
        int upperIntensity = 1;
        int middleIntensity = 5;
        int lowerIntensity = 6;
        int additionalCharacters = (upperIntensity + middleIntensity + lowerIntensity) * baseText.length();
        CursedText cursedText = textConversionService
                .convertToCursedText(baseText, upperIntensity, middleIntensity, lowerIntensity);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSize(baseText.length() + additionalCharacters);
    }

}