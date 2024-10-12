package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.model.users.dto.UserUpdateDTO;
import ru.kolodin.service.main.UserService;

/**
 * REST Контроллер пользователей
 */
@Tag(name = "Контроллер пользователей", description = "CRUD пользователей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class UserRestController {

    private final UserService userService;

    /**
     * Получить список всех пользователей
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return страница пользователей
     */
    @Operation(
            summary = "Получить список всех пользователей"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAll(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize) {
        PageDTO usersPageDTO;
        try {
            usersPageDTO = userService.findAll(pageNumber, pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(usersPageDTO, HttpStatus.OK);
    }

    /**
     * Изменить данные пользователя.
     * @param userUpdateDTO DTO пользователя.
     * @return сообщение о результате.
     */
    @Operation(
            summary = "Изменение данных пользователя."
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/")
    public ResponseEntity<Message> update(
            @RequestBody
            @Parameter(description = "DTO пользователя")
            UserUpdateDTO userUpdateDTO) {
        Message message = userService.update(userUpdateDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить пользователя
     * @param userDTO ДТО пользователя
     * @return сообщение о результате
     */
    @Operation(
            summary = "Удалить пользователя."
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/")
    public ResponseEntity<Message> delete(
            @RequestBody
            @Parameter(description = "DTO пользователя")
            UserDTO userDTO) {
        Message message = new Message();
        try {
            userService.delete(userDTO);
            message.setMessage("Пользователь успешно удален");
        } catch (AnyReasonException e) {
            message.setMessage("Что-то пошло не так " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Заблокировать пользователя.
     * @param userDTO ДТО пользователя
     * @return сообщение о результате
     */
    @Operation(
            summary = "Заблокировать пользователя."
    )
    @PatchMapping("/block")
    public ResponseEntity<Message> blockUser(
            @RequestBody
            @Parameter(description = "DTO пользователя")
            UserDTO userDTO) {
        Message message = new Message();
        try {
            userService.block(userDTO, false);
            message.setMessage("Пользователь заблокирован");
        } catch (AnyReasonException e) {
            message.setMessage("Что-то пошло не так " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Разблокировать пользователя.
     * @param userDTO ДТО пользователя
     * @return сообщение о результате
     */
    @Operation(
            summary = "Разблокировать пользователя."
    )
    @PatchMapping("/unblock")
    public ResponseEntity<Message> unblockUser(
            @RequestBody
            @Parameter(description = "DTO пользователя")
            UserDTO userDTO) {
        Message message = new Message();
        try {
            userService.block(userDTO, true);
            message.setMessage("Пользователь разблокирован");
        } catch (AnyReasonException e) {
            message.setMessage("Что-то пошло не так " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Изменить роль пользователя.
     * @param userDTO ДТО пользователя
     * @return сообщение о результате
     */
    @Operation(
            summary = "Изменить роль пользователя."
    )
    @PatchMapping("/role")
    public ResponseEntity<Message> setRole(
            @RequestBody
            @Parameter(description = "DTO пользователя")
            UserDTO userDTO,
            @RequestBody
            @Parameter(description = "роль пользователя")
            Role role) {
        Message message = new Message();
        try {
            userService.setRole(userDTO, role);
            message.setMessage("Роль пользователя изменена");
        } catch (AnyReasonException e) {
            message.setMessage("Что-то пошло не так " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
