package ru.kolodin.service.calendar;

import org.springframework.stereotype.Service;
import ru.kolodin.model.enums.Period;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Сервис работы с датами
 */
@Service
public class CalendarService {

    private final Calendar calendar;

    public CalendarService() {
        calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
    }

    /**
     * Получить дату со смещением назад относительно текущей даты
     * @param period количество дней
     * @return дата ранее текущей
     */
    public Date getOffsetDateBefore(Period period) {
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, - (period.getValue()));
        return calendar.getTime();
    }
}
