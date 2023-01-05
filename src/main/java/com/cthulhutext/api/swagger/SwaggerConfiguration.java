package com.cthulhutext.api.swagger;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi textConversionGroup(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder().group("text-conversion")
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Text Conversion API").version(appVersion)))
                .packagesToScan("com.cthulhutext.api.conversion")
                .build();
    }
}
