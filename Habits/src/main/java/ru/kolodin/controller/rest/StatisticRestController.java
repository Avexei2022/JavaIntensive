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
import ru.kolodin.model.message.Message;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.statistics.StatisticService;

/**
 * REST Контроллер статистики.
 * Сваггер http://localhost:8082/swagger-ui/index.html
 */
@Tag(name = "Контроллер статистики", description = "Статистика и отчеты")
@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class StatisticRestController {
    private final StatisticService statisticService;

    /**
     * Получить процент выполнения привычек за период.
     * @param userDTO DTO пользователя.
     * @param period Период.
     * @return результат.
     */
    @Operation(
            summary = "Получить процент выполнения привычек за период"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/percentage")
    public ResponseEntity<Message> getPercentage(
            @RequestBody
            @Parameter(description = "ДТО привычки")
            UserDTO userDTO,
            @RequestBody
            @Parameter(description = "Период")
            Period period) {
        Message message = new Message();
        message.setMessage(statisticService.getPercentage(userDTO, period).toString());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
