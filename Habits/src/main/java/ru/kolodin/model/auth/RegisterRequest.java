package ru.kolodin.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс запроса на регистрацию.
 */
@Schema(description = "JКласс запроса на регистрацию")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String username;

    /**
     * Адрес электронной почты пользователя.
     */
    @Schema(description = "Адрес электронной почты пользователя")
    private String email;

    /**
     * Пароль пользователя.
     */
    @Schema(description = "Пароль пользователя")
    private String password;

}
