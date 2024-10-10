package ru.kolodin.service.db.habit;

import org.springframework.data.domain.Page;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.users.User;

/**
 * Сервис привычек
 */
public interface HabitDbService {

    /**
     * Добавить / изменить привычку.
     * @param habit привычка.
     */
    void saveHabit(Habit habit) throws RuntimeException;

    /**
     * Получить привычку по id.
     * @param id - уникальный номер.
     * @return привычка.
     */
    Habit getHabitById(Long id);

    /**
     * Удалить привычку по id.
     * @param id id привычки.
     */
    void deleteHabitById(Long id);

    /**
     * Удалить привычку.
     * @param habit привычка.
     */
    void deleteHabit(Habit habit);

    /**
     * Удалить все привычки.
     */
    void deleteAllHabit();

    /**
     * Удалить все привычки пользователя
     * @param user пользователь
     */
    void deleteAllByUser(User user);

    /**
     * Получить страницу из списка всех привычек.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек.
     */
    Page<Habit> getAllHabit(int pageNumber, int pageSize);

    /**
     * Получить страницу из списка всех привычек пользователя.
     * @param user пользователь.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    Page<Habit> getAllHabitByUser(User user, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка всех привычек пользователя.
     * @param user пользователь.
     * @param frequency периодичность.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    Page<Habit> getAllHabitByUserAndFrequency(
            User user, Frequency frequency, int pageNumber, int pageSize);
}
