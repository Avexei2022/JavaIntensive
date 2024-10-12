package ru.kolodin.model.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kolodin.model.page.DTO;
import ru.kolodin.model.users.Role;

/**
 * ДТО пользователя
 */
@Schema(description = "ДТО пользователя")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends DTO {
    @Schema(description = "ID пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Права доступа пользователя")
    private Role role;
}
