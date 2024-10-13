package ru.kolodin.service.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.model.statistic.Percentage;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.calendar.CalendarService;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final HabitDbService habitDbService;
    private final HabitStatusDbService habitStatusDbService;
    private final CalendarService calendarService;

    public Percentage getPercentage(UserDTO userDTO, Period period) {
        Date dateTo = new Date();
        Date dateFrom = calendarService.getOffsetDateBefore(period);
        List<Habit> habits = habitDbService.getAllByUserEmail(userDTO.getEmail());
        List<HabitStatus> habitStatuses = new ArrayList<>();
        habits.forEach(habit -> {
            habitStatuses.addAll(habitStatusDbService.getAllByHabitIdAndDateBetween(habit.getId(), dateFrom, dateTo));
        });
        Integer total = habitStatuses.size();
        Integer completed = habitStatuses.stream()
                .filter(habitStatus -> habitStatus.getStatus().equals(Status.COMPLETED)).toList().size();
        Integer percent = (completed / total) * 100;
        return new Percentage(
                userDTO.getId(), period, total, completed, percent
        );
    }


}
