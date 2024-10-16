package ru.kolodin.service.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolodin.model.exceptions.ResourceNotFoundException;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.User;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.model.users.dto.UserUpdateDTO;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.user.UserDbService;

/**
 * Сервис пользователей
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserDbService userDbService;
    private final HabitDbService habitDbService;


    public PageDTO findAll(Integer pageNumber, Integer pageSize) {
        return userDbService.findAll(pageNumber, pageSize);
    }

    /**
     * Изменение данных пользователя
     * @param userUpdateDTO пользователь
     * @return сообщение о результате изменений данных
     */
     public Message update(UserUpdateDTO userUpdateDTO) {
        Message message = new Message();
        try {
            User user = userDbService.findById(userUpdateDTO.getId());
            user.setUsername(userUpdateDTO.getUsername());
            user.setEmail(userUpdateDTO.getEmail());
            user.setPassword(userUpdateDTO.getPassword());
            message = userDbService.update(user);
        } catch (ResourceNotFoundException e) {
            message.setMessage("Ресурс временно недоступен. Зайдите позже.");
        }
        return message;
    }

    /**
     * Удалить пользователя
     * @param userDTO ДТО пользователя
     */
    @Transactional
    public void delete(UserDTO userDTO) {
        habitDbService.deleteAllByUser(userDTO.getId());
        userDbService.delete(userDTO.getId());
    }

    /**
     * Заблокировать пользователя
     * @param userDTO ДТО пользователя
     */
    public void block(UserDTO userDTO, Boolean isEnabled) {
        User user = userDbService.findById(userDTO.getId());
        user.setEnabled(isEnabled);
        userDbService.update(user);
    }

    public void setRole(UserDTO userDTO, Role role) {
        User user = userDbService.findById(userDTO.getId());
        user.setRole(role);
        userDbService.update(user);
    }
}
