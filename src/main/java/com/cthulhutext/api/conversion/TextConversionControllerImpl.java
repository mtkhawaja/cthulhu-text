package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@RestController
public class TextConversionControllerImpl implements TextConversionController {
    private final TextConversionService textConversionService;
    Logger logger = LoggerFactory.getLogger(TextConversionControllerImpl.class);

    @Autowired
    public TextConversionControllerImpl(TextConversionService textConversionService) {
        this.textConversionService = textConversionService;
    }

    @Override
    public ResponseEntity<CursedText> getCthulhuText(String text) {
        CursedText cursedText = textConversionService.convertToCursedText(text);
        return new ResponseEntity<>(cursedText, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CursedText> getCthulhuText(String text, int upperIntensity, int middleIntensity, int lowerIntensity) {
        CursedText cursedText = textConversionService.convertToCursedText(text, upperIntensity, middleIntensity, lowerIntensity);
        return new ResponseEntity<>(cursedText, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(Exception ex) {
        return ex.getMessage();
    }

}
