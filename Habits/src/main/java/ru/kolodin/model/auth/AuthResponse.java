package ru.kolodin.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс ответа об аутентификации.
 */
@Schema(description = "Класс ответа об аутентификации")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * JWT - токен.
     */
    @Schema(description = "JWT - токен")
    private String token;

}
