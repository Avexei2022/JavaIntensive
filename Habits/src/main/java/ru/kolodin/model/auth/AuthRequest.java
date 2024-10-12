package ru.kolodin.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс запроса на аутентификацию.
 */
@Schema(description = "Класс запроса на аутентификацию")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    /**
     * Электронный адрес пользователя.
     */
    @Schema(description = "Электронный адрес пользователя")
    private String email;

    /**
     * Пароль пользователя.
     */
    @Schema(description = "Пароль пользователя")
    String password;

}
