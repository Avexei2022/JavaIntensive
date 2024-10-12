package ru.kolodin.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolodin.config.AuthConfig;
import ru.kolodin.config.BasicConfig;
import ru.kolodin.model.auth.AuthRequest;
import ru.kolodin.model.auth.AuthResponse;
import ru.kolodin.model.auth.RegisterRequest;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.User;
import ru.kolodin.repository.UsersRepository;

/**
 * Сервис аутентификации.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Репозиторий пользователей.
     */
    private final UsersRepository usersRepository;

    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Сервис токенов.
     */
    private final JwtService jwtService;

    /**
     * Обработчик запросов на аутентификацию.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Конфигуратор базовых настроек.
     */
    private final BasicConfig basicConfig;

    /**
     * Конфигуратор аутентификации.
     */
    private final AuthConfig authConfig;

    /**
     * Синхронный клиент REST.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Структура данных, представляющая заголовки HTTP-запросов или ответов.
     */
    @Autowired
    private HttpHeaders headers;

    /**
     * Регистрация нового пользователя.
     * @param request запрос на регистрацию.
     * @return ответ с результатом регистрации.
     */
    public AuthResponse userRegister(RegisterRequest request) {
        User user = new User(request.getUsername(),
                request.getEmail(), request.getPassword(), Role.USER);
        usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Аутентификация пользователя
     * @param request запрос.
     * @return ответ с результатом.
     */
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        User user = usersRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

}
