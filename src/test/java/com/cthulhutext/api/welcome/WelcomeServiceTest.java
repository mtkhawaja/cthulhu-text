package com.cthulhutext.api.welcome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class WelcomeServiceTest {
    @Autowired
    WelcomeService welcomeService;
    @Value("${necronomicon.couplet}")
    private String welcomeMessage;

    @DisplayName("Should return Necronomicon Couplet When a welcome message is requested")
    @Test
    void shouldReturnNecronomiconCoupletWhenAWelcomeMessageIsRequested() {
        assertThat(welcomeService.getWelcomeMessage()).isEqualTo(welcomeMessage);
    }

}