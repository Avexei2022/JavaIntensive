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
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.model.habitstatus.dto.HabitStatusDTO;
import ru.kolodin.model.message.Message;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.service.main.HabitStatusService;

import java.util.Date;


/**
 * REST Контроллер привычек.
 */
@Tag(name = "Контроллер статуса привычек", description = "CRUD статуса привычек")
@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class HabitStatusRestController {


    private final HabitStatusService habitStatusService;

    /**
     * Добавить статус привычки в базу данных
     * @param habitStatusDTO ДТО статуса привычки
     * @return сообщение о результате
     */
    @Operation(
            summary = "Добавить статус привычки в базу данных"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/")
    public ResponseEntity<Message> add(
            @RequestBody
            @Parameter(description = "ДТО статуса привычки")
            HabitStatusDTO habitStatusDTO) {
        Message message = new Message();
        try {
            habitStatusService.add(habitStatusDTO);
            message.setMessage("Статус привычки успешно добавлен");
        } catch (RuntimeException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Изменить статус привычки в базе данных.
     * @param habitStatusDTO ДТО статуса привычки.
     * @return сообщение о результате.
     */
    @Operation(
            summary = "Изменить статус привычки в базе данных"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/")
    public ResponseEntity<Message> update(
            @RequestBody
            @Parameter(description = "ДТО статуса привычки")
            HabitStatusDTO habitStatusDTO) {
        Message message = new Message();
        try {
            habitStatusService.update(habitStatusDTO);
            message.setMessage("Статус привычки успешно изменен");
        } catch (RuntimeException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить статус привычки по Id.
     * @param id ID статуса привычки.
     * @return статус привычки.
     */
    @Operation(
            summary = "Получить статус привычки по Id"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<HabitStatusDTO> getById(
            @PathVariable("id")
            @Parameter(description = "ID статуса привычки")
            Long id) {
        HabitStatusDTO habitStatusDTO;
        try {
            habitStatusDTO = habitStatusService.getById(id);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(habitStatusDTO, HttpStatus.OK);
    }

    /**
     * Удалить статус привычки по Id.
     * @param id ID статуса привычки.
     * @return сообщение о результате удаления статуса привычки.
     */
    @Operation(
            summary = "Удалить статус привычки по Id"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteById(
            @PathVariable("id")
            @Parameter(description = "ID статуса привычки")
            Long id) {
        Message message = new Message();
        try {
            habitStatusService.deleteById(id);
            message.setMessage("Статус привычки успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить статус привычки.
     * @param habitStatusDTO ДТО статуса привычки.
     * @return сообщение о результатах удаления статуса привычки.
     */
    @Operation(
            summary = "Удалить статус привычки"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/")
    public ResponseEntity<Message> delete(
            @RequestBody
            @Parameter(description = "ДТО статуса привычки")
            HabitStatusDTO habitStatusDTO) {
        Message message = new Message();
        try {
            habitStatusService.delete(habitStatusDTO);
            message.setMessage("Статус привычки успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить все статусы привычек.
     * @return сообщение о результате удаления статуса привычек.
     */
    @Operation(
            summary = "Удалить все статусы привычек"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/all")
    public ResponseEntity<Message> deleteAll() {
        Message message = new Message();
        try {
            habitStatusService.deleteAll();
            message.setMessage("Все статусы привычек успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить все статусы конкретной привычки
     * @param habitDTO ДТО привычки.
     * @return сообщение о результатах удаления.
     */
    @Operation(
            summary = "Удалить все статусы конкретной привычки"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/habit")
    public ResponseEntity<Message> deleteAllByHabit(
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {
        Message message = new Message();
        try {
            habitStatusService.deleteAllByHabit(habitDTO);
            message.setMessage("Статусы привычки успешно удалены");
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так. " + e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка статусов привычки.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param habitDTO ДТО привычки.
     * @return страница статусов привычки.
     */
    @Operation(
            summary = "Получить страницу из списка статусов привычки"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/habit/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByHabit(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {

        PageDTO habitStatusPageDTO;
        try{
            habitStatusPageDTO = habitStatusService
                    .getAllByHabit(habitDTO,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitStatusPageDTO, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param habitDTO ДТО привычки.
     * @param status статус.
     * @return страница статусов привычки.
     */
    @Operation(
            summary = "Получить страницу из списка статусов привычки с фильтром по статусу"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/habit/status/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByUserAndFrequency(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize,
            @RequestBody
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO,
            @RequestBody
            @Parameter(description = "статус привычки")
            Status status) {

        PageDTO habitStatusPageDTO;
        try{
            habitStatusPageDTO = habitStatusService
                    .getAllByHabitAndStatus(habitDTO,
                            status,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitStatusPageDTO, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по дате.
     * @param dateFrom от даты.
     * @param dateTo до даты.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param habitDTO ДТО привычки.
     * @return страница статусов привычки.
     */
    @Operation(
            summary = "Получить страницу из списка статусов привычки с фильтром по дате."
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/habit/date/between/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllByHabitAndDateBetween(
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
            @Parameter(description = "ДТО привычки")
            HabitDTO habitDTO) {

        PageDTO habitStatusPageDTO;
        try{
            habitStatusPageDTO = habitStatusService
                    .getAllByHabitAndDateBetween(
                            habitDTO,
                            dateFrom,
                            dateTo,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitStatusPageDTO, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по периоду.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param period Предопределенный период (Сегодня/Сутки/Неделя/Месяц)
     * @param habitDTO ДТО привычки.
     * @return страница статусов привычки.
     */
    @Operation(
            summary = "Получить страницу из списка статусов привычки с фильтром по периоду."
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/habit/date/period/{pageNumber}/{pageSize}")
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
            HabitDTO habitDTO) {

        PageDTO habitStatusPageDTO;
        try{
            habitStatusPageDTO = habitStatusService
                    .getAllByHabitAndDatePeriod(
                            habitDTO,
                            period,
                            pageNumber,
                            pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(habitStatusPageDTO, HttpStatus.OK);
    }
}
