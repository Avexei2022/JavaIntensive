package ru.kolodin.model.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kolodin.model.users.dto.UserDTO;

/**
 * Отчет о количестве текущих серий выполнения привычек
 */
@Schema(description = "Отчет о количестве текущих серий выполнения привычек")
@Data
@AllArgsConstructor
public class ReportDailyAndWeeklyUncompleted {

    @Schema(description = "DTO пользователя")
    private UserDTO userDTO;

    @Schema(description = "общее количество ежедневных привычек")
    private Integer totalDaily;

    @Schema(description = "общее количество еженедельных привычек")
    private Integer totalWeekly;

    @Schema(description = "количество ежедневных привычек, которые следует выполнить")
    private Integer uncompletedDaily;

    @Schema(description = "количество еженедельных привычек, которые следует выполнить")
    private Integer uncompletedWeekly;
}


