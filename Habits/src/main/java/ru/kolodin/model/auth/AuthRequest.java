package ru.kolodin.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс запроса на аутентификацию.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    /**
     * Электронный адрес пользователя.
     */
    private String email;

    /**
     * Пароль пользователя.
     */
    String password;

}
