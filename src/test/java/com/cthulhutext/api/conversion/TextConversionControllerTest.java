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
import static org.mockito.Mockito.when;

@SpringBootTest
class TextConversionControllerTest {
    @Mock
    TextConversionService textConversionService;
    private TextConversionController textConversionController;
    private final String text = "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.textConversionController = new TextConversionControllerImpl(textConversionService);
    }

    @DisplayName("Should create CursedText When given valid text")
    @Test
    void shouldCreateCursedTextWhenGivenValidText() {
        when(textConversionService.convertToCursedText(text)).thenReturn(new CursedText(text));
        ResponseEntity<CursedText> cursedTextResponseEntity = textConversionController.getCthulhuText(text);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        assertThat(cursedTextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(text.length());
    }

    @DisplayName("Should create CursedText When given text and all intensities")
    @Test
    void shouldCreateCursedTextWhenGivenTextAndAllIntensities() {
        int upperIntensity = 1;
        int middleIntensity = 5;
        int lowerIntensity = 6;
        when(textConversionService.convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity))
                .thenReturn(new CursedText(text));
        ResponseEntity<CursedText> cursedTextResponseEntity =
                textConversionController.getCthulhuText(text, upperIntensity, middleIntensity, lowerIntensity);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        assertThat(cursedTextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(text.length());

    }

}