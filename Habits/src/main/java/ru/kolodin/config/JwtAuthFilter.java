package ru.kolodin.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kolodin.model.users.User;
import ru.kolodin.service.auth.JwtService;
import ru.kolodin.service.db.user.UserDetailServiceImpl;

import java.io.IOException;

/**
 * Фильтр аутентификации, выполняемый один раз для каждого запроса.
 */
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * Сервис токенов.
     */
    private final JwtService jwtService;

    /**
     * Сервис данных о пользователе.
     */
    private final UserDetailServiceImpl userDetailsService;

    /**
     * Гарантированный вызов только один раз для каждого запроса в рамках одного потока запросов.
     * @param request запрос.
     * @param response ответ.
     * @param filterChain цепочка фильтров.
     * @throws ServletException Общее исключение сервлета.
     * @throws IOException исключение ввода/вывода.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        email = jwtService.extractEmail(jwt);
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = this.userDetailsService.loadUserByUsername(email);
            if(jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
