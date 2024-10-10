package ru.kolodin.service.db.user;

import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.User;
import ru.kolodin.model.users.dto.UserDTO;

/**
 * Сервис пользователей.
 */
public interface UserDbService {

    /**
     * Получить страницу из списка всех пользователей
     * @param pageNumber номер страницы в списке
     * @param pageSize количество пользователей в странице
     * @return запрашиваемая страница из списка пользователей с информационной частью о списке
     */
    PageDTO findAllUser(Integer pageNumber, Integer pageSize);

    /**
     * Получить пользователя по имени
     * @param username - имя пользователя
     * @return пользователь
     */
    User findUserByUsername(String username);

    /**
     * Получить пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return пользователь
     */
    User findUserByEmail(String email);

    /**
     * Получить DTO пользователя по имени
     * @param username - имя пользователя
     * @return DTO пользователя
     */
    UserDTO findUserDTOByUsername(String username);

    /**
     * Получить DTO пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return DTO пользователя
     */
    UserDTO findUserDTOByEmail(String email);

    /**
     * Регистрация нового пользователя
     * @param user Новый пользователь
     * @return сообщение о результате регистрации
     */
    Message registerNewUser(User user);

    /**
     * Проверить наличие пользователя в БД
     * @param id ID пользователя
     * @return результат поиска
     */
    Boolean isExists(Long id);
}
