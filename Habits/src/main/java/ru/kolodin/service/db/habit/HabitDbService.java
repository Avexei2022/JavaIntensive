package ru.kolodin.service.db.habit;

import ru.kolodin.model.habits.Habit;

/**
 * Сервис привычек
 */
public interface HabitDbService {

    /**
     * Получить привычку по id.
     * @param id - уникальный номер.
     * @return привычка.
     */
    Habit getHabitById(Long id);
}
