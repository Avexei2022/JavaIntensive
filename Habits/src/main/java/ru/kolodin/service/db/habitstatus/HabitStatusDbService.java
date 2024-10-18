package ru.kolodin.service.db.habitstatus;

import org.springframework.data.domain.Page;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;

import java.util.Date;
import java.util.List;

/**
 * Сервис базы данных истории привычек
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
     * Удалить статус привычки.
     * @param habitStatus статус привычки.
     */
    void delete(HabitStatus habitStatus);

    /**
     * Удалить все статусы привычек.
     */
    void deleteAll();

    /**
     * Удалить все статусы конкретной привычки.
     * @param habitId  ID привычки.
     */
    void deleteAllByHabit(Long habitId);

    /**
     * Получить страницу из списка статусов привычки.
     * @param habitId ID привычки.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabit(Long habitId, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param habitId ID привычки.
     * @param status статус.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabitAndStatus(
            Long habitId, Status status, int pageNumber, int pageSize);

    /**
     * Получить страницу из списка статусов привычки с фильтром по дате.
     * @param habitId ID привычки.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    Page<HabitStatus> getAllByHabitAndDateBetween(
            Long habitId, Date dateFrom, Date dateTo, int pageNumber, int pageSize);

    /**
     * Получить полный список статусов привычки с фильтром по дате.
     * @param habitId ID привычки.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @return страница статусов привычки.
     */
    List<HabitStatus> getAllByHabitIdAndDateBetween(
            Long habitId, Date dateFrom, Date dateTo);

    /**
     * Проверить наличие статуса привычки в БД
     * @param id ID статуса привычки
     * @return результат поиска
     */
    Boolean isExists(Long id);

    /**
     * Проверить наличие статуса привычки в БД в конкретную дату
     * @param habit Привычка
     * @param date Дата
     * @return результат поиска
     */
    Boolean isExistsByHabitAndDate(Habit habit, Date date);

    /**
     * Проверить наличие статуса привычки в БД в определенный период
     * @param habit Привычка
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @return результат поиска
     */
    Boolean isExistsByHabitAndDateBetween(Habit habit, Date dateFrom, Date dateTo);
}
