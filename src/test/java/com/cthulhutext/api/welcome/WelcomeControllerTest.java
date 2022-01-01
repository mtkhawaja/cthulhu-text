package com.cthulhutext.api.welcome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
class WelcomeControllerTest {
    @Value("${necronomicon.couplet}")
    private String welcomeMessage;

    @Mock
    private WelcomeService welcomeService;

    private WelcomeController welcomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.welcomeController = new WelcomeControllerImpl(welcomeService);
    }

    @DisplayName("Should retrieve welcome message from WelcomeService When saying hello")
    @Test
    void shouldRetrieveWelcomeMessageFromWelcomeServiceWhenSayingHello() {
        when(welcomeService.getWelcomeMessage()).thenReturn(welcomeMessage);
        ResponseEntity<Map<String, String>> response = welcomeController.sayHello();
        verify(welcomeService, atMostOnce()).getWelcomeMessage();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .containsEntry("response", welcomeMessage);
    }

}