package ru.kolodin.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

/**
 * Страница со списком ДТО и информационной частью
 */
@Data
@AllArgsConstructor
public class PageDTO {
    /**
     * Информационная часть страницы
     */
    private PageInfo info;

    /**
     * Список ДТО
     */
    private List<? extends DTO> DTOList;
}
