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
    void save(Habit habit) throws RuntimeException;

    /**
     * Получить привычку по id.
     * @param id - уникальный номер.
     * @return привычка.
     */
    Habit getById(Long id);

    /**
     * Удалить привычку по id.
     * @param id id привычки.
     */
    void deleteById(Long id);

    /**
     * Удалить привычку.
     * @param habit привычка.
     */
    void delete(Habit habit);

    /**
     * Удалить все привычки.
     */
    void deleteAll();

    /**
     * Удалить все привычки пользователя.
     * @param id ID пользователя.
     */
    void deleteAllByUser(Long id);

    /**
     * Получить страницу из списка всех привычек.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек.
     */
    Page<Habit> getAll(int pageNumber, int pageSize);

    /**
     * Получить страницу из списка всех привычек пользователя.
     * @param userId ID пользователя.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    Page<Habit> getAllByUser(Long userId, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка всех привычек пользователя с фильтром периодичности.
     * @param userId ID пользователя.
     * @param frequency периодичность.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    Page<Habit> getAllByUserAndFrequency(
            Long userId, Frequency frequency, int pageNumber, int pageSize);

    /**
     * Проверить наличие привычки в БД
     * @param id ID привычки
     * @return результат поиска
     */
    Boolean isExists(Long id);
}
