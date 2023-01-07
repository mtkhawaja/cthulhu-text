package com.cthulhutext.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CthulhuTextApiApplicationTests {
    @Autowired
    CthulhuTextApiApplication application;

    @DisplayName("Should not fail When application context is loaded")
    @Test
    void shouldNotFailWhenApplicationContextIsLoaded() {
        assertThat(application).isNotNull();
    }

}
