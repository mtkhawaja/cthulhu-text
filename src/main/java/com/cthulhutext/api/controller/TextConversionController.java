package com.cthulhutext.api.controller;

import com.cthulhutext.api.service.TextConversionService;
import com.cthulhutext.openapi.generated.api.ConvertApi;
import com.cthulhutext.openapi.generated.model.CursedText;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextConversionController implements ConvertApi {
    private final TextConversionService textConversionService;

    @Autowired
    public TextConversionController(TextConversionService textConversionService) {
        this.textConversionService = textConversionService;
    }

    @Override
    public ResponseEntity<CursedText> convertTextToCthulhuText(String text, Integer upperIntensity, Integer middleIntensity, Integer lowerIntensity) {
        var cursedText = textConversionService.convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity);
        return ResponseEntity.ok(cursedText);
    }

    @Override
    public ResponseEntity<CursedText> randomlyConvertTextToCthulhuText(String text) {
        var cursedText = textConversionService.convertToCursedText(text);
        return ResponseEntity.ok(cursedText);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(Exception ex) {
        return ex.getMessage();
    }

}
