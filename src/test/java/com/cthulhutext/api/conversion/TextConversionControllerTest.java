package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class TextConversionControllerTest {
    private final String text = "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn";
    @Mock
    TextConversionService textConversionService;
    private TextConversionController textConversionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.textConversionController = new TextConversionControllerImpl(textConversionService);
    }

    @DisplayName("Should generate CursedText with random intensities When given text")
    @Test
    void shouldGenerateCursedTextWithRandomIntensitiesWhenGivenText() {
        when(textConversionService.convertToCursedText(text))
                .thenReturn(new CursedText(text));
        ResponseEntity<CursedText> cursedTextResponseEntity = textConversionController.getCthulhuText(text);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        verify(textConversionService, atMostOnce())
                .convertToCursedText(text);
        assertThat(cursedTextResponseEntity)
                .isNotNull();
        assertThat(cursedTextResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull();
    }

    @DisplayName("Should generate CursedText When given text and all intensities")
    @Test
    void shouldGenerateCursedTextWhenGivenTextAndAllIntensities() {
        int upperIntensity = 1;
        int middleIntensity = 5;
        int lowerIntensity = 6;
        when(textConversionService.convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity))
                .thenReturn(new CursedText(text));
        ResponseEntity<CursedText> cursedTextResponseEntity =
                textConversionController.getCthulhuText(text, upperIntensity, middleIntensity, lowerIntensity);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        verify(textConversionService, atMostOnce())
                .convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity);
        assertThat(cursedTextResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull();
    }

}