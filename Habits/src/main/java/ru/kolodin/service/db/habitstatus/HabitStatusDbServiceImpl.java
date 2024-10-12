package ru.kolodin.service.db.habitstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.repository.HabitStatusesRepository;
import ru.kolodin.service.db.PageService;
import ru.kolodin.service.db.habit.HabitDbService;

import java.util.Date;

/**
 * Сервис базы данных истории привычек
 */
@Service
@RequiredArgsConstructor
public class HabitStatusDbServiceImpl implements HabitStatusDbService {

    private final HabitStatusesRepository habitStatusRepository;

    private final PageService pageService;

    private final HabitDbService habitDbService;

    /**
     * Добавить / изменить статус привычки
     * @param habitStatus привычка
     */
    @Override
    public void save(HabitStatus habitStatus) throws AnyReasonException {
        try {
            if (!habitDbService.isExists(habitStatus.getHabit().getId())) {
                throw new ObjectNotFoundException("Привычка в базе данных не найдена!");
            }
            habitStatusRepository.save(habitStatus);
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так: " + e.getMessage());
        }
    }

    /**
     * Получить статус привычки по id.
     * @param id id статуса привычки.
     * @return статус привычки.
     */
    @Override
    public HabitStatus getById(Long id) {
        return habitStatusRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Статус привычки с id = " + id + " в базе данных не найден."));
    }

    /**
     * Удалить статус привычки по id.
     * @param id id статуса привычки.
     */
    @Override
    public void deleteById(Long id) {
        habitStatusRepository.deleteById(id);
    }

    /**
     * Удалить статус привычки.
     * @param habitStatus статус привычки.
     */
    @Override
    public void delete(HabitStatus habitStatus) {
        habitStatusRepository.delete(habitStatus);
    }

    /**
     * Удалить все статусы привычек.
     */
    @Override
    public void deleteAll() {
        habitStatusRepository.deleteAll();
    }

    /**
     * Удалить все статусы конкретной привычки
     * @param habitID привычка
     */
    @Override
    public void deleteAllByHabit(Long habitID) {
        habitStatusRepository.deleteAllByHabitId(habitID);
    }

    /**
     * Получить страницу из списка статусов привычки.
     * @param habitId ID привычки.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    @Override
    public Page<HabitStatus> getAllByHabit(Long habitId, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitStatusRepository.findAllByHabit(habitId, pageable);
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param habitId ID привычки.
     * @param status статус.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    @Override
    public Page<HabitStatus> getAllByHabitAndStatus(
            Long habitId, Status status, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitStatusRepository.findAllByHabitAndStatus(habitId, status, pageable);
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по дате.
     * @param habitId ID привычки.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    @Override
    public Page<HabitStatus> getAllByHabitAndDateBetween(
            Long habitId, Date dateFrom, Date dateTo, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitStatusRepository.findAllByHabitAndDateBetween(
                habitId, dateFrom, dateTo, pageable);
    }

    /**
     * Проверить наличие статуса привычки в БД
     * @param id ID статуса привычки
     * @return результат поиска
     */
    @Override
    public Boolean isExists(Long id) {
        return habitStatusRepository.existsById(id);
    }
}
