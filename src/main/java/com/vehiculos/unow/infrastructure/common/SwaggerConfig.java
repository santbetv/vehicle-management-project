package com.vehiculos.unow.infrastructure.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiEndPointinfo())
                .externalDocs(new ExternalDocumentation()
                        .description("SpringCuentas Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    private Info apiEndPointinfo() {
        return new Info().title("Unow Vehiculos - By Santiago Betancur Villegas")
                .description("Spring admin application")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }
}
