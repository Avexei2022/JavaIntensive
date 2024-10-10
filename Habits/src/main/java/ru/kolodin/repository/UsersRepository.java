package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.users.User;

import java.util.Optional;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    /**
     * Получить пользователя по имени
     * @param username имя пользователя
     * @return пользователь
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Получить пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return пользователь
     */
    Optional<User> findUserByEmail(String email);
}
