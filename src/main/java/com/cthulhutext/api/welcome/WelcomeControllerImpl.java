package com.cthulhutext.api.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class WelcomeControllerImpl implements WelcomeController {
    private final WelcomeService welcomeService;

    @Autowired
    WelcomeControllerImpl(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }


    @Override
    public ResponseEntity<Map<String, String>> sayHello() {
        return new ResponseEntity<>(Collections.singletonMap("response", welcomeService.getWelcomeMessage()), HttpStatus.OK);
    }
}
