package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Text Conversion", value = "Set of endpoints for converting text to Cthulhu-fied text.")
@RestController
@RequestMapping("/v1/convert")
public interface TextConversionController {
    @ApiOperation(value = "getCthulhuText", notes = "Get Cthulhu-fied text.")
    @GetMapping(value = "/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CursedText> getCthulhuText(@PathVariable String text);
}
