package ru.kolodin.service.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.calendar.CalendarService;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;
import ru.kolodin.service.mapper.HabitMapper;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitDbService habitDbService;
    private final HabitMapper habitMapper;
    private final HabitStatusDbService habitStatusDbService;
    private final CalendarService calendarService;

    /**
     * Добавить привычку в базу данных.
     * @param habitDTO ДТО привычки.
     * @throws RuntimeException исключение.
     */
    public void add(HabitDTO habitDTO) throws RuntimeException{
        try {
            Habit habit = habitMapper.habitDTOToHabit(habitDTO);
            habit.setId(null);
            habitDbService.save(habit);
        } catch (AnyReasonException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Изменить привычку в базе данных.
     * @param habitDTO ДТО привычки.
     * @throws RuntimeException исключение.
     */
    public void update(HabitDTO habitDTO) throws RuntimeException{
        try {
            Habit habit = habitMapper.habitDTOToHabit(habitDTO);
            habitDbService.save(habit);
        } catch (AnyReasonException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Получить привычку по ID.
     * @param id ID привычки.
     * @return привычка.
     * @throws ObjectNotFoundException исключение в случае отсутствия привычки в БД.
     */
    public HabitDTO getById(Long id) throws ObjectNotFoundException{
        try {
            return habitMapper.habitToDTO(habitDbService.getById(id));
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    /**
     * Удалить привычку по ID.
     * @param id ID привычки.
     */
    @Transactional
    public void deleteById(Long id) {
        habitStatusDbService.deleteAllByHabit(id);
        habitDbService.deleteById(id);
    }

    /**
     * Удалить привычку.
     * @param habitDTO ДТО привычки.
     */
    @Transactional
    public void delete(HabitDTO habitDTO) {
        habitStatusDbService.deleteAllByHabit(habitDTO.getId());
        habitDbService.delete(habitMapper.habitDTOToHabit(habitDTO));
    }

    /**
     * Удалить все привычки.
     */
    @Transactional
    public void deleteAll() {
        habitStatusDbService.deleteAll();
        habitDbService.deleteAll();
    }

    /**
     * Удалить все привычки пользователя.
     * @param userDTO ДТО пользователя.
     */
    @Transactional
    public void deleteByUser(UserDTO userDTO) {
        List<Habit> habits = habitDbService.getAllByUserEmail(userDTO.getEmail());
        habits.forEach(habit -> habitStatusDbService.deleteAllByHabit(habit.getId()));
        habitDbService.deleteAllByUser(userDTO.getId());
    }

    /**
     * Получить страницу из списка привычек пользователя.
     * @param userDTO ДТО пользователя.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница привычек пользователя.
     */
    public PageDTO getAllByUser(UserDTO userDTO, Integer pageNumber, Integer pageSize) {
        return habitMapper.habitsToPageDTO(
                habitDbService.getAllByUserId(userDTO.getId(), pageNumber, pageSize));
    }

    /**
     * Получить страницу из списка привычек пользователя.
     * @param userDTO ДТО пользователя.
     * @param frequency периодичность.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница привычек пользователя.
     */
    public PageDTO getAllByUserAndFrequency(UserDTO userDTO,
                                            Frequency frequency,
                                            Integer pageNumber,
                                            Integer pageSize) {
        return habitMapper.habitsToPageDTO(
                habitDbService.getAllByUserAndFrequency(
                        userDTO.getId(), frequency, pageNumber, pageSize)
        );
    }

    /**
     * Получить страницу из списка привычек пользователя с фильтром по дате.
     * @param userDTO ДТО пользователя.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница привычек.
     */
    public PageDTO getAllByUserAndDateBetween(
            UserDTO userDTO, Date dateFrom, Date dateTo, Integer pageNumber, Integer pageSize) {
        return habitMapper.habitsToPageDTO(
                habitDbService.getAllByUserAndDateBetween(
                        userDTO.getId(), dateFrom, dateTo, pageNumber, pageSize)
        );
    }

    /**
     * Получить страницу из списка привычек пользователя с фильтром по периоду создания.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param period Предопределенный период (Сегодня/Сутки/Неделя/Месяц)
     * @param userDTO ДТО пользователя.
     * @return страница статусов привычки.
     */
    public PageDTO getAllByUserAndDatePeriod(
            UserDTO userDTO, Period period, Integer pageNumber, Integer pageSize) {
        Date dateTo = new Date();
        Date dateFrom = calendarService.getOffsetDateBefore(period);
        return habitMapper.habitsToPageDTO(
                habitDbService.getAllByUserAndDateBetween(
                        userDTO.getId(), dateFrom, dateTo, pageNumber, pageSize));
    }


}
