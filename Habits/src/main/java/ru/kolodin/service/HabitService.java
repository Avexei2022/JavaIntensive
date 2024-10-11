package ru.kolodin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.users.dto.UserDTO;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.mapper.HabitMapper;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitDbService habitDbService;
    private final HabitMapper habitMapper;

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
    public void deleteById(Long id) {
        habitDbService.deleteById(id);
    }

    /**
     * Удалить привычку.
     * @param habitDTO ДТО привычки.
     */
    public void delete(HabitDTO habitDTO) {
        habitDbService.delete(habitMapper.habitDTOToHabit(habitDTO));
    }

    /**
     * Удалить все привычки.
     */
    public void deleteAll() {
        habitDbService.deleteAll();
    }

    /**
     * Удалить все привычки.
     * @param userDTO ДТО пользователя.
     */
    public void deleteByUser(UserDTO userDTO) {
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
                habitDbService.getAllByUser(userDTO.getId(), pageNumber, pageSize));
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
                habitDbService.getAllByUserAndFrequency(userDTO.getId(), frequency, pageNumber, pageSize)
        );
    }
}
