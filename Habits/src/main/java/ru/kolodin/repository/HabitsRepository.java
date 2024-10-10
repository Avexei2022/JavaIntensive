package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.habits.Habit;

/**
 * Репозиторий привычек
 */
@Repository
public interface HabitsRepository extends JpaRepository<Habit, Long> {

}
