package ru.kolodin.service.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolodin.model.enums.Period;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.habits.dto.HabitDTO;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.Status;
import ru.kolodin.model.habitstatus.dto.HabitStatusDTO;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.service.calendar.CalendarService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;
import ru.kolodin.service.mapper.HabitStatusMapper;

import java.util.Date;

/**
 * Сервис истории привычек
 */
@Service
@RequiredArgsConstructor
public class HabitStatusService {

    private final HabitStatusDbService habitStatusDbService;
    private final HabitStatusMapper habitStatusMapper;
    private final CalendarService calendarService;

    /**
     * Добавить статус привычки в базу данных.
     * @param habitStatusDTO ДТО статуса привычки.
     * @throws RuntimeException исключение.
     */
    public void add(HabitStatusDTO habitStatusDTO) throws RuntimeException{
        try {
            HabitStatus habitStatus = habitStatusMapper.habitStatusDTOToHabitStatus(habitStatusDTO);
            habitStatus.setId(null);
            habitStatusDbService.save(habitStatus);
        } catch (AnyReasonException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Изменить статус привычки в базе данных.
     * @param habitStatusDTO ДТО статуса привычки.
     * @throws RuntimeException исключение.
     */
    public void update(HabitStatusDTO habitStatusDTO) throws RuntimeException{
        try {
            HabitStatus habitStatus = habitStatusMapper.habitStatusDTOToHabitStatus(habitStatusDTO);
            habitStatusDbService.save(habitStatus);
        } catch (AnyReasonException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Получить статус привычку по ID.
     * @param id ID статуса привычки.
     * @return статус привычка.
     * @throws ObjectNotFoundException исключение в случае отсутствия статуса привычки в БД.
     */
    public HabitStatusDTO getById(Long id) throws ObjectNotFoundException{
        try {
            return habitStatusMapper.habitStatusToDTO(habitStatusDbService.getById(id));
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    /**
     * Удалить статус привычку по ID.
     * @param id ID статуса привычки.
     */
    public void deleteById(Long id) {
        habitStatusDbService.deleteById(id);
    }

    /**
     * Удалить статус привычки.
     * @param habitStatusDTO ДТО статуса привычки.
     */
    public void delete(HabitStatusDTO habitStatusDTO) {
        habitStatusDbService.delete(habitStatusMapper.habitStatusDTOToHabitStatus(habitStatusDTO));
    }

    /**
     * Удалить все статусы привычек.
     */
    public void deleteAll() {
        habitStatusDbService.deleteAll();
    }

    /**
     * Удалить все статусы конкретной привычки.
     * @param habitDTO ДТО привычки.
     */
    public void deleteAllByHabit(HabitDTO habitDTO) {
        habitStatusDbService.deleteAllByHabit(habitDTO.getId());
    }

    /**
     * Получить страницу из списка статусов привычки.
     * @param habitDTO ДТО привычки.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    public PageDTO getAllByHabit(HabitDTO habitDTO, Integer pageNumber, Integer pageSize) {
        return habitStatusMapper.habitStatusToPageDTO(
                habitStatusDbService.getAllByHabit(habitDTO.getId(), pageNumber, pageSize));
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по статусу.
     * @param habitDTO ДТО привычки.
     * @param status статус.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    public PageDTO getAllByHabitAndStatus(HabitDTO habitDTO,
                                            Status status,
                                            Integer pageNumber,
                                            Integer pageSize) {
        return habitStatusMapper.habitStatusToPageDTO(
                habitStatusDbService.getAllByHabitAndStatus(habitDTO.getId(), status, pageNumber, pageSize)
        );
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по дате.
     * @param habitDTO ДТО привычки.
     * @param dateFrom от даты включительно.
     * @param dateTo до даты включительно.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @return страница статусов привычки.
     */
    public PageDTO getAllByHabitAndDateBetween(HabitDTO habitDTO,
                                               Date dateFrom,
                                               Date dateTo,
                                               Integer pageNumber,
                                               Integer pageSize) {
        return habitStatusMapper.habitStatusToPageDTO(
                habitStatusDbService.getAllByHabitAndDateBetween(
                        habitDTO.getId(), dateFrom, dateTo, pageNumber, pageSize)
        );
    }

    /**
     * Получить страницу из списка статусов привычки с фильтром по периоду.
     * @param pageNumber номер страницы.
     * @param pageSize размер страницы.
     * @param period Предопределенный период (День/Неделя/Месяц)
     * @param habitDTO ДТО привычки.
     * @return страница статусов привычки.
     */
    public PageDTO getAllByHabitAndDatePeriod(HabitDTO habitDTO, Period period, Integer pageNumber, Integer pageSize) {
        Date dateTo = new Date();
        Date dateFrom = calendarService.getOffsetDateBefore(period);
        return habitStatusMapper.habitStatusToPageDTO(
                habitStatusDbService.getAllByHabitAndDateBetween(
                        habitDTO.getId(), dateFrom, dateTo, pageNumber, pageSize));

    }
}
