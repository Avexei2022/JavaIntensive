package ru.kolodin.model.users.dto;

import lombok.*;
import ru.kolodin.model.page.DTO;
import ru.kolodin.model.users.Role;

/**
 * ДТО пользователя
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends DTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
