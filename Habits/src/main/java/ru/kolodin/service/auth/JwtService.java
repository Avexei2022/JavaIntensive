package ru.kolodin.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import ru.kolodin.model.users.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервис токенов
 */
@Service
public class JwtService {

    /**
     * Секретный ключ подписи.
     */
    private static final String SECRET_KEY = "cnUuZ2IuZ3JvdXA1OTg0LnN0dWRlbnQuZ3JhZHVhdGlvblByb2plY3Q=";

    /**
     * Извлечь из токена адрес электронной почты.
     * @param token токен.
     * @return адрес электронной почты.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечь данные из токена
     * @param token токен
     * @param claimsResolver функция извлечения данных
     * @param <T> тип данных
     * @return данные
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Получить токен.
     * @param user данные пользователя
     * @return токен
     */
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    /**
     * Генератор токена.
     * @param extraClaims дополнительные данные.
     * @param user данные пользователя.
     * @return токен.
     */
    public String generateToken(Map<String,
            Object> extraClaims,
                                User user) {

        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * Проверить токен на действительность.
     * @param token токен.
     * @param user данные пользователя.
     * @return true, если токен действителен.
     */
    public boolean isTokenValid(String token, User user) {
        final String email = extractEmail(token);
        return  (email.equals(user.getEmail())) && !isTokenExpired(token);
    }

    /**
     * Проверить токен на истечение срока действия.
     * @param token токен
     * @return true, если токен просрочен.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечь дату истечения срока действия токена.
     * @param token токен.
     * @return дата истечения.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечь все данные из токена.
     * @param token токен.
     * @return данные.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получить ключ для подписи токена.
     * @return ключ.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
