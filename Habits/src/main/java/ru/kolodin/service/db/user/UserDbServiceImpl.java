package ru.kolodin.service.db.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kolodin.model.auth.RegisterRequest;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ResourceNotFoundException;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.User;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.repository.UsersRepository;
import ru.kolodin.service.auth.AuthService;
import ru.kolodin.service.db.PageService;
import ru.kolodin.service.mapper.UserMapper;

/**
 * Сервис БД пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserDbServiceImpl implements UserDbService {

    /**
     * Репозиторий пользователей.
     */
    private final UsersRepository usersRepository;

    /**
     * Сервис страниц.
     */
    private final PageService pageService;

    /**
     * Конвертер пользователей.
     */
    private final UserMapper userMapper;

    /**
     * Сервис аутентификации.
     */
    private final AuthService authService;


    /**
     * Получить страницу из списка всех пользователей
     * @param pageNumber номер страницы в списке
     * @param pageSize количество пользователей в странице
     * @return запрашиваемая страница из списка пользователей с информационной частью о списке
     */
    @Override
   public PageDTO findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        Page<User> userResultPage;
        try {
            userResultPage = usersRepository.findAll(pageable);
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так: " + e.getMessage());
        }
        return userMapper.usersToPageDTO(userResultPage);
    }

    @Override
    public User findById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * Получить пользователя по имени
     * @param username - имя пользователя
     * @return пользователь
     */
    @Override
    public User findByUsername(String username) {
        return usersRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * Получить пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return пользователь
     */
    @Override
    public User findByEmail(String email) {
        return usersRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * Получить DTO пользователя по имени
     * @param username - имя пользователя
     * @return DTO пользователя
     */
    @Override
    public UserDTO findDTOByUsername(String username) {
        User user = usersRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return userMapper.userToDTO(user);
    }

    /**
     * Получить DTO пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return DTO пользователя
     */
    @Override
    public UserDTO findDTOByEmail(String email) {
        User user = usersRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return userMapper.userToDTO(user);
    }

    /**
     * Изменение данных пользователя
     * @param user пользователь
     * @return сообщение о результате изменений данных
     */
    @Override
    public Message update(User user) {
        Message message = new Message();
        try {
            usersRepository.save(user);
            message.setMessage("Данные пользователя успешно изменены");
        } catch (ResourceNotFoundException e) {
            message.setMessage("Ресурс временно недоступен. Зайдите позже.");
        }
        return message;
    }

    /**
     * Удаление пользователя из базы данных
     * @param userId ID пользователя
     */
    @Override
    public void delete(Long userId) {
        try {
            usersRepository.deleteById(userId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Ресурс временно недоступен. Зайдите позже.");
        }
    }

    /**
     * Регистрация нового пользователя
     * @param user Новый пользователь
     * @return сообщение о результате регистрации
     */
    @Override
    public Message registerNew(User user) {
        Message message = new Message();
        String newUserEmail = user.getEmail();
        if (usersRepository.findUserByEmail(newUserEmail).isPresent()) {
            message.setMessage("Пользователь с таким именем уже зарегистрирован");
        } else {
            try {
                authService.userRegister(new RegisterRequest(user.getUsername()
                        , newUserEmail
                        , user.getPassword()));
                message.setMessage("Вы успешно зарегистрированы!");
            } catch (RuntimeException e) {
                message.setMessage("Ресурс временно недоступен. Зайдите позже.");
            }
        }
        return message;
    }

        /**
     * Проверить наличие пользователя в БД
     * @param id ID пользователя
     * @return результат поиска
     */
    @Override
    public Boolean isExists(Long id) {
        return usersRepository.existsById(id);
    }
}
