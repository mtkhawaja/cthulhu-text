package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Tag(name = "Text Conversion", description = "Set of endpoints for converting text to Cthulhu-fied text.")
@RequestMapping("/v1/convert")
@Validated
public interface TextConversionController {
    @Operation(summary = "getCthulhuText", description = "Generated Cthulhu-fied text using random intensities.")
    @GetMapping(value = "/random/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CursedText> getCthulhuText(
            @Parameter(description = "The text to be converted", required = true, example = "Damn it, it wasn't quite fresh enough!")
            @PathVariable String text
    );

    @Operation(summary = "getCthulhuText", description = "Generate Cthulhu-fied text.")
    @GetMapping(value = "/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CursedText> getCthulhuText(
            @Parameter(description = "The text to be converted", required = true, example = "This is a dream. It's fiction.")
            @PathVariable String text,
            @Parameter(description = "The intensity [1 - 100] of upper diacritical marks such as U+0300, U+0301 etc.")
            @RequestParam(defaultValue = "1", value = "upperIntensity") @Min(1) @Max(100) int upperIntensity,
            @Parameter(description = "The intensity [1 - 100] of middle diacritical marks such as U+0310, U+0311 etc.")
            @RequestParam(defaultValue = "1", value = "middleIntensity") @Min(1) @Max(100) int middleIntensity,
            @Parameter(description = "The intensity [1 - 100] of lower diacritical marks such as U+0316, U+0317 etc.")
            @RequestParam(defaultValue = "1", value = "lowerIntensity") @Min(1) @Max(100) int lowerIntensity
    );
}
