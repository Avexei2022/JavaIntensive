package ru.kolodin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.kolodin.service.db.user.UserDbService;

/**
 * Конфигуратор безопасности.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Фильтр аутентификации.
     */
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Поставщик проверки подлинности имени пользователя и пароля.
     */
    private final AuthenticationProvider authenticationProvider;

    private final UserDbService userDbService;

    /**
     * Сервис учетных записей.
     * @return учетная запись.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userDbService::findByEmail;
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

    /**
     * Правила фильтрации.
     * @param http защищенный http запрос.
     * @return цепочка фильтров.
     * @throws Exception общие исключения.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST,"/auth/**"
                                        , "/register/**")
                                .permitAll()
                                .requestMatchers("/v3/**",
                                        "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest()
                                .authenticated())
//                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/"))
                .sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
