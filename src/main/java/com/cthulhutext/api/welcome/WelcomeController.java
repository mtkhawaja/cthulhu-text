package com.cthulhutext.api.welcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "Welcome Controller")
@RequestMapping({"v1"})
public interface WelcomeController {
    @Operation(description = "Displays Welcome message.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, String>> sayHello();
}
