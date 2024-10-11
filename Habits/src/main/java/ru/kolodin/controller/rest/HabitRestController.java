package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.User;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.HabitService;


/**
 * REST Контроллер привычек.
 * Сваггер http://localhost:8081/swagger-ui/index.html
 */
@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/habit")
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class HabitRestController {

    private final HabitService habitService;

    /**
     * Добавить привычку в базу данных
     * @param habitDTO ДТО привычки
     * @return сообщение о результате со статусом
     */
    @SecurityRequirement(name = "JWT")
    @PostMapping("/add")
    public ResponseEntity<Message> add(@RequestBody HabitDTO habitDTO) {
        Message message = new Message();
        try {
            habitService.add(habitDTO);
            message.setMessage("Привычка успешно добавлена");
        } catch (RuntimeException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Изменить привычку в базе данных
     * @param habitDTO ДТО привычки
     * @return сообщение о результате со статусом
     */
    @SecurityRequirement(name = "JWT")
    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody HabitDTO habitDTO) {
        Message message = new Message();
        try {
            habitService.update(habitDTO);
            message.setMessage("Привычка успешно изменена");
        } catch (RuntimeException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить привычку по Id.
     * @param id ID привычки.
     * @return привычка.
     */
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(
            @PathVariable("id") Long id) {
        HabitDTO habitDTO;
        try {
            habitDTO = habitService.getById(id);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(habitDTO, HttpStatus.OK);
    }

    /**
     * Удалить привычку по Id.
     * @param id ID привычки.
     * @return сообщение о результате удаления привычки.
     */
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteById(
            @PathVariable("id") Long id) {
        Message message = new Message();
        try {
            habitService.deleteById(id);
            message.setMessage("Привычка успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить привычку.
     * @param habitDTO ДТО привычки.
     * @return сообщение о результатах удаления привычки.
     */
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/habit")
    public ResponseEntity<Message> delete(
            @RequestBody HabitDTO habitDTO) {
        Message message = new Message();
        try {
            habitService.delete(habitDTO);
            message.setMessage("Привычка успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить все привычки.
     * @return сообщение о результате удаления привычек.
     */
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/all")
    public ResponseEntity<Message> deleteAll() {
        Message message = new Message();
        try {
            habitService.deleteAll();
            message.setMessage("Все привычки успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить все привычки пользователя.
     * @param userDTO ДТО пользователя.
     * @return сообщение о результатах удаления привычки.
     */
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/user")
    public ResponseEntity<Message> deleteByUser(
            @RequestBody UserDTO userDTO) {
        Message message = new Message();
        try {
            habitService.deleteByUser(userDTO);
            message.setMessage("Привычки пользователя успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка привычек с фильтром по пользователю.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param userDTO ДТО пользователя.
     * @return страница привычек пользователя.
     */
    @SecurityRequirement(name = "JWT")
    @GetMapping("/user/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUser(
            @PathVariable("pageNumber") Integer pageNumber,
            @PathVariable("pageSize") Integer pageSize,
            @RequestBody UserDTO userDTO) {

        PageDTO habitsPageDTO;
        try{
            habitsPageDTO = habitService
                    .getAllByUser(userDTO,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitsPageDTO, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка привычек с фильтром по пользователю.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param userDTO ДТО пользователя.
     * @param frequency периодичность.
     * @return страница привычек пользователя.
     */
    @SecurityRequirement(name = "JWT")
    @GetMapping("/frequency/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUserAndFrequency(
            @PathVariable("pageNumber") Integer pageNumber,
            @PathVariable("pageSize") Integer pageSize,
            @RequestBody UserDTO userDTO,
            @RequestBody Frequency frequency) {

        PageDTO habitsPageDTO;
        try{
            habitsPageDTO = habitService
                    .getAllByUserAndFrequency(userDTO,
                            frequency,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitsPageDTO, HttpStatus.OK);
    }

}
