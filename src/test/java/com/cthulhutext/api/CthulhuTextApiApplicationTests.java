package com.cthulhutext.api;

import com.cthulhutext.api.welcome.WelcomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CthulhuTextApiApplicationTests {
    @Autowired
    WelcomeController welcomeController;

    @Test
    void contextLoads() {
        assertThat(welcomeController).isNotNull();
    }

}
