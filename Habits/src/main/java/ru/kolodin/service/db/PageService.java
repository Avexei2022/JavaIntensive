package ru.kolodin.service.db;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Сервис страниц
 */
@Service
@NoArgsConstructor
public class PageService {
    /**
     * Получить интерфейс параметров страницы
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return интерфейс параметров страницы
     */
    public Pageable getPageable(int pageNumber, int pageSize) {
        pageNumber = pageNumber - 1;
        if (pageNumber < 0) pageNumber = 0;
        return PageRequest.of(pageNumber, pageSize);
    }
}
