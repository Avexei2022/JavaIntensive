package ru.kolodin.model.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kolodin.model.users.dto.UserDTO;

/**
 * Отчет о прогрессе выполнения ежедневных/еженедельных привычек
 * на текущую дату или за последние 7 дней соответственно.
 */
@Schema(description = "Отчет о прогрессе выполнения ежедневных/еженедельных привычек")
@Data
public class ReportDailyOrWeeklyProgress {

    @Schema(description = "DTO пользователя")
    private UserDTO userDTO;

    @Schema(description = "Общее количество привычек")
    private Integer total;

    @Schema(description = "Количество выполненных привычек")
    private Integer completed;

    @Schema(description = "Процент выполнения привычек")
    private Integer percent;

    public ReportDailyOrWeeklyProgress(UserDTO userDTO, Integer total, Integer completed) {
        this.userDTO = userDTO;
        this.total = total;
        this.completed = completed;
        percent = getPercent();
    }

    @Schema(description = "Получить процент выполнения привычек")
    public Integer getPercent() {
        return (completed / total) * 100;
    }
}
