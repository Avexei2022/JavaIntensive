package ru.kolodin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Конфигуратор аутентификации.
 * Получает первоначальные учетные данные администратора,
 * заданные по умолчанию в application.yaml
 */
@Component
@ConfigurationProperties(prefix = "auth-data")
//@ComponentScan
@Getter
@Setter
public class AuthConfig {

    /**
     * Имя администратора
     */
    private String username;

    /**
     * Начальный адрес электронной почты / логин администратора
     */
    private String email;

    /**
     * Начальный пароль администратора
     */
    private String password;
}
