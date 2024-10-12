package ru.kolodin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Habit Controller Api",
                description = "Application to control habit",
                version = "1.0.0",
                contact = @Contact(
                        name = "Aleksei Kolodin"
                )
        )
)
public class OpenApiConfig {
}
