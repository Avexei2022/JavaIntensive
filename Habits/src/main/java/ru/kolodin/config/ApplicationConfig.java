package ru.kolodin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kolodin.repository.UsersRepository;


/**
 * Конфигуратор приложения.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AuthConfig authConfig;
    private final UsersRepository usersRepository;

    /**
     * Сервис учетных записей.
     * @return учетная запись.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usersRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Обработчик аутентификации.
     * @return обработчик.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Кодировщик пароля.
     * @return экземпляр кодировщика.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Менеджер аутентификации.
     * @param config конфигурация проверки подлинности.
     * @return экземпляр менеджера аутентификации.
     * @throws Exception исключение.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
