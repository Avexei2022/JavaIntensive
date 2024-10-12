package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * * Сваггер http://localhost:8082/swagger-ui/index.html
 */
@Tag(name = "Контроллер аутентификации", description = "Регистрация и аутентификация пользователей")
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
    @Operation(
            summary = "Регистрация нового пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody
            @Parameter(description = "Запрос на регистрацию с данными пользователя")
            RegisterRequest request) {
        return ResponseEntity.ok(authService.userRegister(request));
    }

    /**
     * Аутентификация пользователя
     * @param request запрос на аутентификацию.
     * @return токен.
     */
    @Operation(
            summary = "Аутентификация пользователя"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody
            @Parameter(description = "Запрос аутентификации с данными пользователя")
            AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
