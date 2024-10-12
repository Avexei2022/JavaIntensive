package ru.kolodin.service.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.page.PageInfo;
import ru.kolodin.model.users.User;
import ru.kolodin.model.users.dto.UserCreationDTO;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.model.users.dto.UserUpdateDTO;

import java.util.List;

/**
 * Конвертер пользователей
 */
@Component
public class UserMapper {

    /**
     * Получить DTO - пользователь
     * @param user пользователь
     * @return DTO - пользователь
     */
    public UserDTO userToDTO(User user) {
        return new UserDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }

    /**
     * Получить DTO - список пользователей
     * @param userList список пользователей
     * @return DTO - список пользователей
     */
    public List<UserDTO> usersListToDTO(List<User> userList) {
        return  userList.stream().map(this::userToDTO).toList();
    }

    /**
     * Получить пользователя из регистрационных данных
     * @param userCreationDTO регистрационные данные
     * @return пользователь
     */
    public User creationDtoToUser(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getUsername(),
                userCreationDTO.getEmail(),
                userCreationDTO.getPassword(),
                userCreationDTO.getRole());
    }

    /**
     * Получить страницу из списка пользователей с информационной частью
     * @param userPage страница пользователей из БД
     * @return DTO - страница пользователей
     */
    public PageDTO usersToPageDTO(Page<User> userPage) {
        return new PageDTO(new PageInfo(userPage.getTotalElements(), userPage.getTotalPages(),
                userPage.getNumber() + 1, userPage.hasNext(), userPage.hasPrevious()),
                usersListToDTO(userPage.getContent()));

    }

}
