package ru.kolodin.service.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.model.statistic.ReportCompletedForPeriod;
import ru.kolodin.model.statistic.ReportDailyAndWeeklyUncompleted;
import ru.kolodin.model.statistic.ReportDailyOrWeeklyProgress;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.calendar.CalendarService;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Сервис статистики и отчетов
 */
@Schema(description = "Сервис статистики и отчетов")
@Service
@RequiredArgsConstructor
public class StatisticService {
    private final HabitDbService habitDbService;
    private final HabitStatusDbService habitStatusDbService;
    private final CalendarService calendarService;

    /**
     * Получить отчет о проценте выполнения привычки за определенный в запросе период
     * @param userDTO ДТО пользователя.
     * @param period Период
     * @return отчет о проценте выполнения привычки за период
     */
    @Schema(description = "Получить отчет о проценте выполнения привычки за определенный в запросе период")
    public ReportCompletedForPeriod getReportCompletedForPeriod(UserDTO userDTO, Period period) {
        List<HabitStatus> habitStatuses = getHabitStatuses(userDTO, period);
        Integer total = habitStatuses.size();
        Integer completed = habitStatuses.stream()
                .filter(habitStatus -> habitStatus.getStatus().equals(Status.COMPLETED)).toList().size();
        return new ReportCompletedForPeriod(
                userDTO, period, total, completed);
    }

    /**
     * Получить отчет о прогрессе выполнения ежедневных привычек за текущую дату
     * или еженедельных привычек за последние 7 дней.
     * @param userDTO ДТО пользователя.
     * @return отчет о прогрессе выполнения ежедневных/еженедельных привычек
     */
    @Schema(description = "Получить отчет о прогрессе выполнения ежедневных/еженедельных привычек")
    public ReportDailyOrWeeklyProgress getReportDailyOrWeeklyProgress(UserDTO userDTO, Frequency frequency) {
        Period period = Period.NOW;
        if (frequency.equals(Frequency.WEEKLY)) period = Period.WEEK;
        List<HabitStatus> habitStatuses = getHabitStatuses(userDTO, period)
                .stream()
                .filter(habitStatus -> habitStatus.getHabit().getFrequency().equals(frequency))
                .toList();
        Integer total = habitStatuses.size();
        Integer completed = habitStatuses.stream()
                .filter(habitStatus -> habitStatus.getStatus().equals(Status.COMPLETED)).toList().size();
        return new ReportDailyOrWeeklyProgress(userDTO, total, completed);
    }

    /**
     * Получить отчет о количестве текущих серий выполнения привычек
     * @param userDTO ДТО пользователя.
     * @return отчет о количестве текущих серий выполнения привычек
     */
    @Schema(description = "Получить отчет о количестве текущих серий выполнения привычек")
    public ReportDailyAndWeeklyUncompleted getReportDailyAndWeeklyUncompleted(UserDTO userDTO) {
        List<HabitStatus> habitStatusesDaily = getHabitStatuses(userDTO, Period.NOW)
                .stream()
                .filter(habitStatus -> habitStatus.getHabit().getFrequency().equals(Frequency.DAILY))
                .toList();
        List<HabitStatus> habitStatusesWeekly = getHabitStatuses(userDTO, Period.WEEK)
                .stream()
                .filter(habitStatus -> habitStatus.getHabit().getFrequency().equals(Frequency.WEEKLY))
                .toList();
        Integer totalDaily = habitStatusesDaily.size();
        Integer uncompletedDaily = habitStatusesDaily.stream()
                .filter(habitStatus -> habitStatus.getStatus().equals(Status.WAITING))
                .toList().size();
        Integer totalWeekly = habitStatusesWeekly.size();
        Integer uncompletedWeekly = habitStatusesWeekly.stream()
                .filter(habitStatus -> habitStatus.getStatus().equals(Status.WAITING))
                .toList().size();
        return new ReportDailyAndWeeklyUncompleted(
                userDTO, totalDaily, totalWeekly, uncompletedDaily, uncompletedWeekly);
    }

    /**
     * Получить список статусов привычек за определенный в запросе период
     * @param userDTO ДТО пользователя
     * @param period период для расчета
     * @return список статусов привычек
     */
    private List<HabitStatus> getHabitStatuses(UserDTO userDTO, Period period) {
        List<Habit> habits = habitDbService.getAllByUserEmail(userDTO.getEmail());
        Date dateTo = new Date();
        Date dateFrom = calendarService.getOffsetDateBefore(period);
        List<HabitStatus> habitStatuses = new ArrayList<>();
        habits.forEach(habit -> {
            habitStatuses.addAll(habitStatusDbService.getAllByHabitIdAndDateBetween(habit.getId(), dateFrom, dateTo));
        });
        return habitStatuses;
    }
}
