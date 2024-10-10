package ru.kolodin.service.db.habit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.repository.HabitsRepository;

import java.util.Optional;

/**
 * Сервис привычек.
 */
@Service
@RequiredArgsConstructor
public class HabitDbServiceImpl implements HabitDbService {

    private final HabitsRepository habitsRepository;

    /**
     * Получить привычку по id.
     * @param id id привычки.
     * @return привычка.
     */
    @Override
    public Habit getHabitById(Long id) {
        return habitsRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Привычка с id = " + id + " в базе данных не найдена"));
    }
}
