package ru.kolodin.service.db.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kolodin.model.users.User;
import ru.kolodin.repository.UsersRepository;

/**
 * Сервис учетных данных пользователя,
 * переопределенный для аутентификации и авторизации по адресу электронной почты
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    /**
     * Переопределение UserDetailsService для аутентификации по электронной почте
     * @param email адрес электронной почты
     * @return Пользователь
     * @throws UsernameNotFoundException исключение при отсутствии пользователя в базе данных
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
