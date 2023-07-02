package com.example.spacex.rockets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI rocketsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpaceX Rockets API")
                        .description("SpaceX Rockets application")
                        .version("v0.0.1"));
    }
}
