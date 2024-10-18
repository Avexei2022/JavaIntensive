package ru.kolodin.model.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.users.dto.UserDTO;


/**
 * Процент выполнения привычек за определенный период.
 */
@Schema(description = "Процент выполнения привычек за определенный период")
@Data
public class ReportCompletedForPeriod {

    @Schema(description = "DTO пользователя")
    private UserDTO userDTO;

    @Schema(description = "Период")
    private Period period;

    @Schema(description = "Общее количество привычек необходимое для выполнения")
    private Integer total;

    @Schema(description = "Количество выполненных привычек")
    private Integer completed;

    @Schema(description = "Процент выполнения привычек")
    private Integer percent;

    public ReportCompletedForPeriod(UserDTO userDTO, Period period, Integer total, Integer completed) {
        this.userDTO = userDTO;
        this.period = period;
        this.total = total;
        this.completed = completed;
        percent = getPercent();
    }

    @Schema(description = "Получить процент выполнения привычек")
    public Integer getPercent() {
        return (completed / total) * 100;
    }


}
