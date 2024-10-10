package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.habitstatus.HabitStatus;

/**
 * Репозиторий статуса привычек.
 */
@Repository
public interface HabitStatusesRepository extends JpaRepository<HabitStatus, Long> {
}
