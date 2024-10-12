package ru.kolodin.model.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kolodin.model.users.Role;

/**
 * ДТО пользователя для регистрации
 */
@Schema(description = "ДТО пользователя для регистрации")
@Data
@AllArgsConstructor
public class UserCreationDTO {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Права доступа пользователя")
    private Role role;
}
