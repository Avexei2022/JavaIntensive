package ru.kolodin.model.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

/**
 * Страница со списком ДТО и информационной частью
 */
@Schema(description = "Страница со списком ДТО и информационной частью")
@Data
@AllArgsConstructor
public class PageDTO {
    /**
     * Информационная часть страницы
     */
    @Schema(description = "Информационная часть страницы")
    private PageInfo info;

    /**
     * Список ДТО
     */
    @Schema(description = "Список ДТО")
    private List<? extends DTO> DTOList;
}
