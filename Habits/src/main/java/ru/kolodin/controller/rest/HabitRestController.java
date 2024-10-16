package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.entity.HabitService;

import java.util.Date;


/**
 * REST Контроллер привычек.
 * Сваггер http://localhost:8082/swagger-ui/index.html
 */
@Tag(name = "Контроллер привычек", description = "CRUD привычек")
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
    @Operation(
            summary = "Добавить привычку в базу данных"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/")
    public ResponseEntity<Message> add(
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {
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
    @Operation(
            summary = "Изменить привычку в базе данных"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/")
    public ResponseEntity<Message> update(
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {
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
    @Operation(
            summary = "Получить привычку по Id"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(
            @PathVariable("id")
            @Parameter(description = "ID привычки")
            Long id) {
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
    @Operation(
            summary = "Удалить привычку по Id"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteById(
            @PathVariable("id")
            @Parameter(description = "ID привычки")
            Long id) {
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
    @Operation(
            summary = "Удалить привычку"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/")
    public ResponseEntity<Message> delete(
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {
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
    @Operation(
            summary = "Удалить все привычки"
    )
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
    @Operation(
            summary = "Удалить все привычки пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/user")
    public ResponseEntity<Message> deleteByUser(
            @RequestBody
            @Parameter(description = "ДТО пользователя")
            UserDTO userDTO) {
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
    @Operation(
            summary = "Получить страницу из списка привычек с фильтром по пользователю"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/user/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUser(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestBody
            @Parameter(description = "ДТО пользователя")
            UserDTO userDTO) {

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
     * Получить страницу из списка привычек пользователя с фильтром по периодичности.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param userDTO ДТО пользователя.
     * @param frequency периодичность.
     * @return страница привычек пользователя.
     */
    @Operation(
            summary = "Получить страницу из списка привычек пользователя с фильтром по периодичности"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/frequency/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUserAndFrequency(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestBody
            @Parameter(description = "ДТО привычки")
            UserDTO userDTO,
            @RequestParam
            @Parameter(description = "периодичность привычки")
            Frequency frequency) {

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

    /**
     * Получить страницу из списка привычек пользователя с фильтром по дате.
     * @param dateFrom от даты.
     * @param dateTo до даты.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param userDTO ДТО пользователя.
     * @return страница привычек.
     */
    @Operation(
            summary = "Получить страницу из списка привычек пользователя с фильтром по дате."
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/user/date/between/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUserAndDateBetween(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestParam("dateFrom")
            @Parameter(description = "от даты")
            Date dateFrom,
            @RequestParam("dateTo")
            @Parameter(description = "до даты")
            Date dateTo,
            @RequestBody
            @Parameter(description = "ДТО пользователя")
            UserDTO userDTO) {

        PageDTO habitPageDTO;
        try{
            habitPageDTO = habitService
                    .getAllByUserAndDateBetween(
                            userDTO,
                            dateFrom,
                            dateTo,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitPageDTO, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка привычек пользователя с фильтром по периоду.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param period Предопределенный период (Сегодня/Сутки/Неделя/Месяц)
     * @param userDTO ДТО пользователя.
     * @return страница привычек.
     */
    @Operation(
            summary = "Получить страницу из списка привычек пользователя с фильтром по периоду."
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/user/date/period/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByHabitAndDatePeriod(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestParam("period")
            @Parameter(description = "Предопределенный период (Сегодня/Сутки/Неделя/Месяц)")
            Period period,
            @RequestBody
            @Parameter(description = "ДТО привычки")
            UserDTO userDTO) {

        PageDTO habitPageDTO;
        try{
            habitPageDTO = habitService
                    .getAllByUserAndDatePeriod(
                            userDTO,
                            period,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitPageDTO, HttpStatus.OK);
    }
}
