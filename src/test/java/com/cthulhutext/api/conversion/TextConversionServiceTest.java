package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TextConversionServiceTest {
    @Autowired
    private TextConversionService textConversionService;

    @DisplayName("Should create CursedText When given a regular text String")
    @Test
    void shouldCreateCursedTextWhenGivenRegularText() {
        String baseText = "In his house at R'lyeh dead Cthulhu waits dreaming";
        CursedText cursedText = textConversionService.convertToCursedText(baseText);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(baseText.length());
    }

}