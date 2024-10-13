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
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.statistic.ReportCompletedForPeriod;
import ru.kolodin.model.statistic.ReportDailyOrWeeklyProgress;
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
     * Получить отчет о количестве и проценте выполнения привычек за определенный в запросе период.
     * @param userDTO DTO пользователя.
     * @param period Период.
     * @return результат.
     */
    @Operation(
            summary = "Получить отчет о количестве и проценте выполнения привычек за определенный в запросе период."
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/percentage")
    public ResponseEntity<ReportCompletedForPeriod> getPercentage(
            @RequestBody
            @Parameter(description = "ДТО пользователя")
            UserDTO userDTO,
            @RequestBody
            @Parameter(description = "Период")
            Period period) {
        ReportCompletedForPeriod percentage = statisticService.getReportCompletedForPeriod(userDTO, period);
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    /**
     * Получить отчет о прогрессе выполнения ежедневных/еженедельных привычек
     * @param userDTO DTO пользователя.
     * @param frequency Периодичность привычки.
     * @return результат.
     */
    @Operation(
            summary = "Получить отчет о прогрессе выполнения ежедневных/еженедельных привычек"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/progress")
    public ResponseEntity<ReportDailyOrWeeklyProgress> getProgress(
            @RequestBody
            @Parameter(description = "ДТО пользователя")
            UserDTO userDTO,
            @RequestBody
            @Parameter(description = "Периодичность")
            Frequency frequency) {
        ReportDailyOrWeeklyProgress progress = statisticService.getReportDailyOrWeeklyProgress(userDTO, frequency);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
}
