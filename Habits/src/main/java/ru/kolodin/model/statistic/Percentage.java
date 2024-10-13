package ru.kolodin.model.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kolodin.model.enums.Period;


/**
 * Процент выполнения привычек за определенный период.
 */
@Schema(description = "Процент выполнения привычек за определенный период")
@Data
@AllArgsConstructor
public class Percentage {

    @Schema(description = "ID пользователя")
    private Long userId;

    @Schema(description = "Период")
    private Period period;

    @Schema(description = "Общее количество привычек необходимое для выполнения")
    private Integer total;

    @Schema(description = "Количество выполненных привычек")
    private Integer completed;

    @Schema(description = "Процент выполнения привычек")
    private Integer percent;
}
