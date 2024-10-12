package ru.kolodin.model.habitstatus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.model.page.DTO;

import java.util.Date;

/**
 * Класс ДТО привычки
 */
@Schema(description = "Класс ДТО привычки")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitStatusDTO extends DTO {
    /**
     * Уникальный идентификатор статуса
     */
    @Schema(description = "Уникальный идентификатор статуса")
    private Long id;

    /**
     * Привычка
     */
    @Schema(description = "Привычка")
    private HabitDTO habitDTO;

    /**
     * Дата выполнения привычки
     */
    @Schema(description = "Дата выполнения привычки")
    private Date date;

    /**
     * Статус выполнения привычки
     */
    @Schema(description = "Статус выполнения привычки")
    private Status status;
}
