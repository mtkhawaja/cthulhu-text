package com.cthulhutext.api.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    private final String welcomeMessage;

    @Autowired
    public WelcomeServiceImpl(@Value("${necronomicon.couplet}") String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Override
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
