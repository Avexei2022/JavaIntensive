package ru.kolodin.model.users;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "username")
    private String username;

    /**
     * Адрес электронной почты
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Пароль
     */
    @Column(name = "password")
    private String password;

    /**
     * Полномочия пользователя - роль.
     */
    @Column(name = "role")
    private  Role role;

    /**
     * Действующий или нет
     */
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * Получить список полномочия пользователя.
     * @return список полномочий.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Получить пароль
     * @return пароль
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Получить имя пользователя
     * @return имя пользователя
     */
    @Override
    public String getUsername() {
        return username;
    }

}
