package ru.kolodin.service.db.habit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.habits.Habit;
import ru.kolodin.repository.HabitsRepository;
import ru.kolodin.service.db.PageService;
import ru.kolodin.service.db.user.UserDbService;

import java.util.Date;
import java.util.List;


/**
 * Сервис привычек.
 */
@Service
@RequiredArgsConstructor
public class HabitDbServiceImpl implements HabitDbService {

    private final HabitsRepository habitsRepository;

    private final UserDbService userDbService;

    private final PageService pageService;

    /**
     * Добавить / изменить привычку
     * @param habit привычка
     */
    @Override
    public void save(Habit habit) throws ObjectNotFoundException, AnyReasonException {
        try {
            if (habit.getId() != null && !userDbService.isExists(habit.getId())) {
                throw new ObjectNotFoundException("Владелец привычки в базе данных не найден!");
            }
            habitsRepository.save(habit);
        } catch (RuntimeException e) {
            throw new AnyReasonException("Что-то пошло не так: " + e.getMessage());
        }
    }

    /**
     * Получить привычку по id.
     * @param id id привычки.
     * @return привычка.
     */
    @Override
    public Habit getById(Long id) {
        return habitsRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Привычка с id = " + id + " в базе данных не найдена"));
    }

    /**
     * Удалить привычку по id.
     * @param id id привычки.
     */
    @Override
    public void deleteById(Long id) {
        habitsRepository.deleteById(id);
    }

    /**
     * Удалить привычку.
     * @param habit привычка.
     */
    @Override
    public void delete(Habit habit) {
        habitsRepository.delete(habit);
    }

    /**
     * Удалить все привычки.
     */
    @Override
    public void deleteAll() {
        habitsRepository.deleteAll();
    }

    /**
     * Удалить все привычки пользователя.
     * @param id ID пользователя.
     */
    @Override
    public void deleteAllByUser(Long id) {
        habitsRepository.deleteAllByUserId(id);
    }

    /**
     * Получить страницу из списка всех привычек.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек.
     */
    @Override
    public Page<Habit> getAll(int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitsRepository.findAll(pageable);
    }

    /**
     * Получить список всех привычек.
     * @return список привычек.
     */
    @Override
    public List<Habit> getAll() {
        return habitsRepository.findAll();
    }

    /**
     * Получить страницу из списка всех привычек пользователя.
     * @param userId ID пользователя.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    @Override
    public Page<Habit> getAllByUserId(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitsRepository.findAllByUserId(userId, pageable);
    }

    /**
     * Получить список всех привычек пользователя.
     * @param email email пользователя.
     * @return страница список привычек пользователя.
     */
    @Override
    public List<Habit> getAllByUserEmail(String email) {
        return habitsRepository.findAllByUserEmail(email);
    }

    /**
     * Получить страницу из списка всех привычек пользователя с фильтром периодичности.
     * @param userId ID пользователя.
     * @param frequency периодичность.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница списка привычек пользователя.
     */
    @Override
    public Page<Habit> getAllByUserAndFrequency(Long userId, Frequency frequency, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitsRepository.findAllByUserAndFrequency(userId, frequency, pageable);
    }

    /**
     * Получить страницу из списка привычек пользователя с фильтром по дате создания.
     * @param userId ID пользователя.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    @Override
    public Page<Habit> getAllByUserAndDateBetween(
            Long userId, Date dateFrom, Date dateTo, int pageNumber, int pageSize) {
        Pageable pageable = pageService.getPageable(pageNumber, pageSize);
        return habitsRepository.findAllByUserAndDateBetween(
                userId, dateFrom, dateTo, pageable);
    }

    /**
     * Проверить наличие привычки в БД
     * @param id ID привычки
     * @return результат поиска
     */
    @Override
    public Boolean isExists(Long id) {
        return habitsRepository.existsById(id);
    }

}
