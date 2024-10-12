package ru.kolodin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.model.habitstatus.HabitStatus;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий привычек.
 */
@Repository
public interface HabitsRepository extends JpaRepository<Habit, Long> {

    /**
     * Удалить все привычки пользователя.
     * @param id ID пользователя.
     */
    void deleteAllByUserId(Long id);

    /**
     * Получить страницу из списка привычек пользователя.
     * @param id ID пользователя.
     * @param pageable запрашиваемая страница.
     * @return страница из списка привычек пользователя.
     */
    Page<Habit> findAllByUserId(Long id, Pageable pageable);

    /**
     * Получить список привычек пользователя.
     * @param email E-mail пользователя.
     * @return Список привычек пользователя.
     */
    List<Habit> findAllByUserEmail(String email);

    /**
     * Получить страницу из списка привычек пользователя с фильтром периодичности.
     * @param id ID пользователя.
     * @param frequency периодичность.
     * @param pageable запрашиваемая страница.
     * @return страница из списка привычек пользователя.
     */
    Page<Habit> findAllByUserAndFrequency(long id, Frequency frequency, Pageable pageable);

    /**
     * Получить страницу из списка привычек пользователя с фильтром по периоду дат.
     * @param id ID пользователя.
     * @param dateFrom от даты.
     * @param dateTo до даты.
     * @param pageable запрашиваемая страница.
     * @return страница из списка привычек пользователя.
     */
    Page<Habit> findAllByUserAndDateBetween(Long id, Date dateFrom, Date dateTo, Pageable pageable);
}
