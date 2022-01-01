package com.cthulhutext.api.welcome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WelcomeControllerHttpTest {
    @LocalServerPort
    private int port;

    @Value("${necronomicon.couplet}")
    private String welcomeMessage;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("Should display welcome message When base api endpoint is hit")
    @Test
    void shouldDisplayWelcomeMessageWhenBaseApiEndpointIsHit() {
        String endpoint = String.format("http://localhost:%s/v1/", port);
        var response = testRestTemplate.getForObject(endpoint, String.class);
        assertThat(response)
                .isNotNull()
                .contains(welcomeMessage);

    }

}