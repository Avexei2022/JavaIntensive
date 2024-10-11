package ru.kolodin.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.model.page.PageInfo;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.habitstatus.dto.HabitStatusDTO;
import ru.kolodin.service.db.habit.HabitDbService;


import java.util.List;

/**
 * Конвертер статуса привычек.
 */
@RequiredArgsConstructor
public class HabitStatusMapper {

    private final HabitMapper habitMapper;
    private final HabitDbService habitDbService;

    /**
     * Получить DTO статуса привычки.
     * @param habitStatus статус привычки.
     * @return DTO статуса привычки.
     */
    public HabitStatusDTO habitStatusToDTO(HabitStatus habitStatus) {
        return new HabitStatusDTO(habitStatus.getId(),
                habitMapper.habitToDTO(habitStatus.getHabit()),
                habitStatus.getDate(),
                habitStatus.getStatus());
    }

    /**
     * Получить список DTO статуса привычек.
     * @param habitStatusList список статуса привычек.
     * @return список DTO статуса.
     */
    public List<HabitStatusDTO> habitSStatusListToDTO(List<HabitStatus> habitStatusList) {
        return habitStatusList.stream().map(this :: habitStatusToDTO).toList();
    }

    /**
     * Получить статус привычки.
     * @param habitStatusDTO DTO статуса привычки.
     * @return статус привычки.
     */
    public HabitStatus habitStatusDTOToHabitStatus(HabitStatusDTO habitStatusDTO) {
        return new HabitStatus(habitStatusDTO.getId(),
                habitDbService.getById(habitStatusDTO.getHabitDTO().getId()),
                habitStatusDTO.getDate(), habitStatusDTO.getStatus());
    }

    /**
     * Получить страницу списка статуса привычек с информационной частью.
     * @param habitStatusPage страница статуса привычек из БД.
     * @return страница DTO статуса привычек.
     */
    public PageDTO habitStatusToPageDTO (Page<HabitStatus> habitStatusPage) {
        return new PageDTO(new PageInfo(habitStatusPage.getTotalElements(),
                habitStatusPage.getTotalPages(), habitStatusPage.getNumber() + 1,
                habitStatusPage.hasNext(), habitStatusPage.hasPrevious()),
                habitSStatusListToDTO(habitStatusPage.getContent()));

    }
}
