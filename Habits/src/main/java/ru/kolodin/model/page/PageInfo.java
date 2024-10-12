package ru.kolodin.model.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информационная часть страницы
 */
@Schema(description = "Информационная часть страницы")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    /**
     * Общее количество объектов в списке
     */
    @Schema(description = "Общее количество объектов в списке")
    private Long count;

    /**
     * Количество страниц в общем списке
     */
    @Schema(description = "Количество страниц в общем списке")
    private Integer pages;

    /**
     * Номер текущей страницы
     */
    @Schema(description = "Номер текущей страницы")
    private Integer current;

    /**
     * Наличие следующей страницы
     */
    @Schema(description = "Наличие следующей страницы")
    private Boolean hasNext;

    /**
     * Наличие предыдущей страницы
     */
    @Schema(description = "Наличие предыдущей страницы")
    private Boolean hasPrevious;
}
