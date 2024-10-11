package ru.kolodin.service.db.habitstatus;

import org.springframework.data.domain.Page;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;

import java.util.Date;

/**
 * Сервис истории привычек
 */
public interface HabitStatusDbService {

    /**
     * Добавить / изменить статус привычки
     * @param habitStatus привычка
     */
    void save(HabitStatus habitStatus);

    /**
     * Получить статус привычки по id.
     * @param id id статуса привычки.
     * @return статус привычки.
     */
    HabitStatus getById(Long id);

    /**
     * Удалить статус привычки по id.
     * @param id id статуса привычки.
     */
    void deleteById(Long id);

    /**
     * Удалить статус привычку.
     * @param habitStatus статус привычки.
     */
    void delete(HabitStatus habitStatus);

    /**
     * Удалить все статусы привычек.
     */
    void deleteAll();

    /**
     * Удалить все статусы конкретной привычки
     * @param habit привычка
     */
    void deleteAllByHabit(Habit habit);

    /**
     * Получить страницу из списка статусов привычки.
     * @param habit привычка.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabit(Habit habit, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param habit привычка.
     * @param status статус.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabitAndStatus(
            Habit habit, Status status, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка статусов привычки с фильтром по дате.
     * @param habit привычка.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabitAndDateBetween(
            Habit habit, Date dateFrom, Date dateTo, int pageNumber, int pageSize);

    /**
     * Проверить наличие статуса привычки в БД
     * @param id ID статуса привычки
     * @return результат поиска
     */
    Boolean isExists(Long id);
}
