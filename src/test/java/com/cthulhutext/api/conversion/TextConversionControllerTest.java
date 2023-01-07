package com.cthulhutext.api.conversion;

import com.cthulhutext.api.service.TextConversionService;
import com.cthulhutext.openapi.generated.api.ConvertApi;
import com.cthulhutext.openapi.generated.model.CursedText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class TextConversionControllerTest {
    private final String text = "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn";
    @MockBean
    private TextConversionService textConversionService;
    @Autowired
    private ConvertApi textConversionController;

    @DisplayName("Should generate CursedText with random intensities When given text")
    @Test
    void shouldGenerateCursedTextWithRandomIntensitiesWhenGivenText() {
        when(textConversionService.convertToCursedText(text))
                .thenReturn(new CursedText().content(text));
        ResponseEntity<CursedText> cursedTextResponseEntity = textConversionController.randomlyConvertTextToCthulhuText(text);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        verify(textConversionService, atMostOnce())
                .convertToCursedText(text);
        assertThat(cursedTextResponseEntity)
                .isNotNull();
        assertThat(cursedTextResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.getContent())
                .isNotNull();
    }

    @DisplayName("Should generate CursedText When given text and all intensities")
    @Test
    void shouldGenerateCursedTextWhenGivenTextAndAllIntensities() {
        int upperIntensity = 1;
        int middleIntensity = 5;
        int lowerIntensity = 6;
        when(textConversionService.convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity))
                .thenReturn(new CursedText().content(text));
        ResponseEntity<CursedText> cursedTextResponseEntity =
                textConversionController.convertTextToCthulhuText(text, upperIntensity, middleIntensity, lowerIntensity);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        verify(textConversionService, atMostOnce())
                .convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity);
        assertThat(cursedTextResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.getContent())
                .isNotNull();
    }

}