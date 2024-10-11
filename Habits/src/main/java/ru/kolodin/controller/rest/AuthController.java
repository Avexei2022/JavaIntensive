package ru.kolodin.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolodin.model.auth.AuthRequest;
import ru.kolodin.model.auth.AuthResponse;
import ru.kolodin.model.auth.RegisterRequest;
import ru.kolodin.service.auth.AuthService;

/**
 * REST-контроллер регистрации и аутентификации
 * * Сваггер http://localhost:8081/swagger-ui/index.html
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Сервис аутентификации.
     */
    private final AuthService authService;

    /**
     * Регистрация нового пользователя.
     * @param request данные запроса на регистрацию.
     * @return аутентификация.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.userRegister(request));
    }

    /**
     * Аутентификация пользователя
     * @param request запрос на аутентификацию.
     * @return токен.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
