package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TextConversionControllerHttpTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String baseEndpoint;

    @BeforeEach
    void setUp(){
        baseEndpoint = String.format("http://localhost:%s/v1/convert/", port);
    }

    @DisplayName("Should generate CursedText When the conversion endpoint is hit")
    @Test
    void shouldGenerateCursedTextWhenTheConversionEndpointIsHit() {
        String text = "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn.";
        String endpoint = baseEndpoint + text;
        CursedText cursedText = testRestTemplate.getForObject(endpoint, CursedText.class);
        assertThat(cursedText).isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(text.length());

    }

}