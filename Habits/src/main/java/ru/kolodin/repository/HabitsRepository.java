package ru.kolodin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;

/**
 * Репозиторий привычек.
 */
@Repository
public interface HabitsRepository extends JpaRepository<Habit, Long> {

    /**
     * Удалить все привычки пользователя.
     * @param id ID пользователя.
     */
    void deleteAllByUser(Long id);

    /**
     * Получить страницу из списка привычек пользователя.
     * @param id ID пользователя.
     * @param pageable запрашиваемая страница.
     * @return страница из списка привычек пользователя.
     */
    Page<Habit> findAllByUser(Long id, Pageable pageable);

    /**
     * Получить страницу из списка привычек пользователя.
     * @param id ID пользователя.
     * @param frequency периодичность.
     * @param pageable запрашиваемая страница.
     * @return страница из списка привычек пользователя.
     */
    Page<Habit> findAllByUserAndFrequency(long id, Frequency frequency, Pageable pageable);
}
