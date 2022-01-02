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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.textConversionController = new TextConversionControllerImpl(textConversionService);
    }

    @DisplayName("Should create CursedText When given valid text")
    @Test
    void shouldCreateCursedTextWhenGivenValidText() {
        String inputText = "Some long string";
        when(textConversionService.convertToCursedText(inputText)).thenReturn(new CursedText(inputText));
        ResponseEntity<CursedText> cursedTextResponseEntity = textConversionController.getCthulhuText(inputText);
        CursedText cursedText = cursedTextResponseEntity.getBody();
        assertThat(cursedTextResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(inputText.length());
    }







}