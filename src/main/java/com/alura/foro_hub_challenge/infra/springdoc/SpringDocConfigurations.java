package com.alura.foro_hub_challenge.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Foro Hub API")
                        .description("API Rest para el Challenge de Oracle Next Education & Alura Latam " +
                                "Desarrollado por Nicole Fernández. " +
                                "[GitHub](https://github.com/Zhainy) | " +
                                "[LinkedIn](https://www.linkedin.com/in/niferng/)")
                        .contact(new Contact()
                                .name("Nicole Fernández")
                                .email("ni.ferng@gmail.com")));
    }
}
