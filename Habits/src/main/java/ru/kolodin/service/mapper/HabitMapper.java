package ru.kolodin.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.page.PageInfo;
import ru.kolodin.service.db.user.UserDbService;

import java.util.List;

/**
 * Конвертер привычек.
 */
@Component
@RequiredArgsConstructor
public class HabitMapper {

    private final UserMapper userMapper;
    private final UserDbService userDbService;

    /**
     * Получить DTO привычки.
     * @param habit привычка.
     * @return DTO привычки.
     */
    public HabitDTO habitToDTO(Habit habit) {
        return new HabitDTO(habit.getId(),
                habit.getTitle(),
                habit.getDescription(),
                habit.getFrequency(),
                userMapper.userToDTO(habit.getUser()),
                habit.getDate());
    }

    /**
     * Получить список DTO привычек.
     * @param habitList список привычек.
     * @return список DTO привычек.
     */
    public List<HabitDTO> habitListToDTO(List<Habit> habitList) {
        return habitList.stream().map(this :: habitToDTO).toList();
    }

    /**
     * Получить привычку.
     * @param habitDTO DTO привычки.
     * @return привычка.
     */
    public Habit habitDTOToHabit(HabitDTO habitDTO) {
        return new Habit(habitDTO.getId(), habitDTO.getTitle(), habitDTO.getDescription(), habitDTO.getFrequency(),
                userDbService.findByEmail(habitDTO.getUserDTO().getEmail()), habitDTO.getCreationDate());
    }

    /**
     * Получить страницу из списка привычек с информационной частью.
     * @param habitsPage страница привычек из БД.
     * @return страница DTO привычек.
     */
    public PageDTO habitsToPageDTO (Page<Habit> habitsPage) {

        return new PageDTO(new PageInfo(habitsPage.getTotalElements(),
                habitsPage.getTotalPages(), habitsPage.getNumber() + 1,
                habitsPage.hasNext(), habitsPage.hasPrevious()),
                habitListToDTO(habitsPage.getContent()));

    }

}
