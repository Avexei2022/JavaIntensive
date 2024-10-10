package ru.kolodin.model.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kolodin.model.users.Role;

/**
 * ДТО пользователя для регистрации
 */
@Data
@AllArgsConstructor
public class UserCreationDTO {
    private String username;
    private String email;
    private String password;
    private Role role;
}
