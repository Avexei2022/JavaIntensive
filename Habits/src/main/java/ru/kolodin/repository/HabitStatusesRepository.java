package ru.kolodin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий статуса привычек.
 */
@Repository
public interface HabitStatusesRepository extends JpaRepository<HabitStatus, Long> {

    /**
     * Удалить все статусы конкретной привычки.
     * @param id ID привычки.
     */
    void deleteAllByHabitId(Long id);

    /**
     * Получить страницу из списка статусов привычки.
     * @param id ID привычки.
     * @param pageable запрашиваемая страница.
     * @return страница из списка статусов привычки.
     */
    Page<HabitStatus> findAllByHabit(Long id, Pageable pageable);

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param id ID привычки.
     * @param status статус привычки
     * @param pageable запрашиваемая страница.
     * @return страница из списка статусов привычки.
     */
    Page<HabitStatus> findAllByHabitAndStatus(Long id, Status status, Pageable pageable);

    /**
     * Получить страницу из списка статусов привычки с фильтром по периоду дат.
     * @param id ID привычки.
     * @param dateFrom от даты.
     * @param dateTo до даты.
     * @param pageable запрашиваемая страница.
     * @return страница из списка статусов привычки.
     */
    Page<HabitStatus> findAllByHabitAndDateBetween(Long id, Date dateFrom, Date dateTo, Pageable pageable);

    /**
     * Получить страницу из списка статусов привычки с фильтром по периоду дат.
     * @param id ID привычки.
     * @param dateFrom от даты.
     * @param dateTo до даты.
     * @return страница из списка статусов привычки.
     */
    List<HabitStatus> findAllByHabitIdAndDateBetween(Long id, Date dateFrom, Date dateTo);

}
