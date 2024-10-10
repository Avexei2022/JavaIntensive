package ru.kolodin.service.db.habitstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.repository.HabitStatusesRepository;

/**
 * Сервис истории привычек
 */
@Service
@RequiredArgsConstructor
public class HabitStatusDbServiceImpl implements HabitStatusDbService {

    private final HabitStatusesRepository habitStatusRepository;
}
