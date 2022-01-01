package com.cthulhutext.api.welcome;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Api(tags = "Welcome Controller")
@RequestMapping({"v1"})
public interface WelcomeController {
    @ApiOperation(value = "Displays Welcome message.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, String>> sayHello();


}
