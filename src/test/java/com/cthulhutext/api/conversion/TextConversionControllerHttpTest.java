package com.cthulhutext.api.conversion;

import com.cthulhutext.api.models.CursedText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.cthulhutext.api.conversion.ConversionTestUtils.expectedCharacterCountForCursedText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TextConversionControllerHttpTest {
    private final int MAXIMUM_INTENSITY = 100;
    private final int MINIMUM_INTENSITY = 1;
    private final int DEFAULT_INTENSITY = 1;
    private final String text = "God! What wonder that across the earth a great architect went mad";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private String baseEndpoint;
    private String baseRandomEndpoint;

    @BeforeEach
    void setUp() {
        baseRandomEndpoint = String.format("http://localhost:%s/v1/convert/random/%s", port, text);
        baseEndpoint = String.format("http://localhost:%s/v1/convert/%s", port, text);
    }

    @DisplayName("Should generate CursedText at least as long as the original text When the random conversion endpoint is hit")
    @Test
    void shouldGenerateCursedTextAtLeastAsLongAsTheOriginalTextWhenTheRandomConversionEndpointIsHit() {
        CursedText cursedText = testRestTemplate.getForObject(baseRandomEndpoint, CursedText.class);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(text.length());
    }

    @DisplayName("Should generate CursedText using defaults When the conversion endpoint is hit with no intensities")
    @Test
    void shouldGenerateCursedTextUsingDefaultsWhenTheConversionEndpointIsHitWithNoIntensities() {
        CursedText cursedText = testRestTemplate.getForObject(baseEndpoint, CursedText.class);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSize(expectedCharacterCountForCursedText(text, DEFAULT_INTENSITY, DEFAULT_INTENSITY, DEFAULT_INTENSITY));
    }

    @DisplayName("Should generate CursedText using defaults When the conversion endpoint is hit with some but not all intensities")
    @Test
    void shouldGenerateCursedTextUsingDefaultsWhenTheConversionEndpointIsHitWithSomeButNotAllIntensities() {
        int upperIntensity = 50;
        String endpoint = baseEndpoint + "?upperIntensity=" + upperIntensity;
        CursedText cursedText = testRestTemplate.getForObject(endpoint, CursedText.class);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSize(expectedCharacterCountForCursedText(text, upperIntensity, DEFAULT_INTENSITY, DEFAULT_INTENSITY));
    }

    @DisplayName("Should generate CursedText When the conversion endpoint is hit with all intensities")
    @Test
    void shouldGenerateCursedTextWhenTheConversionEndpointIsHitWithAllIntensities() {
        int upperIntensity = 1;
        int middleIntensity = 5;
        int lowerIntensity = 6;
        String endpoint = createEndpointWithIntensities(upperIntensity, middleIntensity, lowerIntensity);
        CursedText cursedText = testRestTemplate.getForObject(endpoint, CursedText.class);
        assertThat(cursedText)
                .isNotNull();
        assertThat(cursedText.content())
                .isNotNull()
                .hasSize(expectedCharacterCountForCursedText(text, upperIntensity, middleIntensity, lowerIntensity));
    }

    @DisplayName("Should respond with a bad http request code 400 When given intensities less than the minimum allowed value")
    @Test
    void shouldRespondWithABadHttpRequestCode400WhenGivenIntensitiesLessThanTheMinimumAllowedValue() {
        int badIntensityArgument = MINIMUM_INTENSITY - 1;
        String endpoint = createEndpointWithIntensities(badIntensityArgument, badIntensityArgument, badIntensityArgument);
        ResponseEntity<String> result = testRestTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("Should respond with a bad http request code 400 When given intensities bigger than the maximum allowed value")
    @Test
    void shouldRespondWithABadHttpRequestCode400WhenGivenIntensitiesBiggerThanTheMaximumAllowedValue() {
        int badIntensityArgument = MAXIMUM_INTENSITY + 1;
        String endpoint = createEndpointWithIntensities(badIntensityArgument, badIntensityArgument, badIntensityArgument);
        ResponseEntity<String> result = testRestTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private String createEndpointWithIntensities(int upperIntensity, int middleIntensity, int lowerIntensity) {
        return String.format("%s?upperIntensity=%s&middleIntensity=%s&lowerIntensity=%s",
                baseEndpoint, upperIntensity, middleIntensity, lowerIntensity);
    }


}