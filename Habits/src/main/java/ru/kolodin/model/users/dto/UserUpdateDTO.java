package ru.kolodin.model.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kolodin.model.users.Role;

/**
 * ДТО изменений данных пользователя
 */
@Schema(description = "ДТО изменений данных пользователя")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @Schema(description = "ID пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Пароль")
    private String password;
}
