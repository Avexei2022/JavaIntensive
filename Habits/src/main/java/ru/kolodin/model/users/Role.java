package ru.kolodin.model.users;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Список полномочий/ролей.
 */
@Schema(description = "Список полномочий/ролей")
public enum Role {
    ADMIN,
    USER
}
