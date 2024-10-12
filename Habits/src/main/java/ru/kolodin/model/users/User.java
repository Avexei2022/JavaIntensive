package ru.kolodin.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Пользователь
 */
@Schema(description = "Пользователь")
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails {

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Уникальный номер.
     */
    @Schema(description = "Уникальный номер")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private long id;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    @Column(name = "username")
    private String username;

    /**
     * Адрес электронной почты
     */
    @Schema(description = "Адрес электронной почты")
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Пароль
     */
    @Schema(description = "Пароль")
    @Column(name = "password")
    private String password;

    /**
     * Полномочия пользователя - роль.
     */
    @Schema(description = "Полномочия пользователя - роль")
    @Column(name = "role")
    private  Role role;

    /**
     * Действующий или нет
     */
    @Schema(description = "Действующий или нет")
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * Получить список полномочия пользователя.
     * @return список полномочий.
     */
    @Schema(description = "Получить список полномочия пользователя.")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Получить пароль
     * @return пароль
     */
    @Schema(description = "Получить пароль")
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Получить имя пользователя
     * @return имя пользователя
     */
    @Schema(description = "Получить имя пользователя")
    @Override
    public String getUsername() {
        return username;
    }

}
