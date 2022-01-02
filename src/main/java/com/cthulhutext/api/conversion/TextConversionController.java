package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Api(tags = "Text Conversion", value = "Set of endpoints for converting text to Cthulhu-fied text.")
@RestController
@RequestMapping("/v1/convert")
public interface TextConversionController {
    @ApiOperation(value = "getCthulhuText", notes = "Get Cthulhu-fied text.")
    @GetMapping(value = "/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CursedText> getCthulhuText(@PathVariable String text);

    @ApiOperation(value = "getCthulhuText", notes = "Get Cthulhu-fied text.")
    @GetMapping(value = "/{text}", produces = MediaType.APPLICATION_JSON_VALUE, params = {"upperIntensity", "middleIntensity", "lowerIntensity"})
    ResponseEntity<CursedText> getCthulhuText(
            @ApiParam(value = "The text to be converted", required = true)
            @PathVariable String text,
            @ApiParam(value = "The intensity of upper diacritical marks such as U+0300, U+0301 etc.")
            @RequestParam(required = false, defaultValue = "1", value = "upperIntensity") @Min(1) @Max(10) int upperIntensity,
            @ApiParam("The intensity of middle diacritical marks such as U+0310, U+0311 etc.")
            @RequestParam(required = false, defaultValue = "1", value = "middleIntensity") @Min(1) @Max(10) int middleIntensity,
            @ApiParam("The intensity of lower diacritical marks such as U+0316, U+0317 etc.")
            @RequestParam(required = false, defaultValue = "1", value = "lowerIntensity") @Min(1) @Max(10) int lowerIntensity

    );
}
