package ru.kolodin.service.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.service.calendar.CalendarService;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;

import java.util.Date;
import java.util.List;

/**
 * Сервис генерации статусов привычки
 */
@Service
@RequiredArgsConstructor
public class HabitStatusCreatorService {
    private final HabitDbService habitDbService;
    private final HabitStatusDbService habitStatusDbService;
    private final CalendarService calendarService;

    /**
     * Генерация ежедневных статусов привычек на текущую дату для всех пользователей.
     */
    @Scheduled(cron = "@daily")
    public void createAllDailyHabitStatusForCurrentDay() {
        List<Habit> habits = habitDbService.getAllByFrequency(Frequency.DAILY);
        habits.forEach(habit -> {
                    if (!habitStatusDbService.isExistsByHabitAndDate(habit, new Date())) {
                        habitStatusDbService.save(new HabitStatus(habit, new Date(), Status.WAITING));
                    }
                });
    }

    /**
     * Генерация еженедельных статусов привычек на текущую дату для всех пользователей.
     */
    @Scheduled(cron = "@daily")
    public void createAllWeeklyHabitStatusForCurrentDay() {
        List<Habit> habits = habitDbService.getAllByFrequency(Frequency.WEEKLY);
        Date dateFrom = calendarService.getOffsetSevenDayBeforeIncludingToday();
        habits.forEach(habit -> {
                    if (!habitStatusDbService.isExistsByHabitAndDateBetween(habit, dateFrom, new Date())) {
                        habitStatusDbService.save(new HabitStatus(habit, new Date(), Status.WAITING));
                    }
                });
    }

}
