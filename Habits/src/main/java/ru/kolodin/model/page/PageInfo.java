package ru.kolodin.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информационная часть страницы
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    /**
     * Общее количество объектов в списке
     */
    private Long count;

    /**
     * Количество страниц в общем списке
     */
    private Integer pages;

    /**
     * Номер текущей страницы
     */
    private Integer current;

    /**
     * Наличие следующей страницы
     */
    private Boolean hasNext;

    /**
     * Наличие предыдущей страницы
     */
    private Boolean hasPrevious;
}
