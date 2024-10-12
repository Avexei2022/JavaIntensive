package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.page.PageDTO;
import ru.kolodin.service.db.user.UserDbService;

/**
 * REST Контроллер пользователей
 */
@Tag(name = "Контроллер пользователей", description = "CRUD пользователей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class UserRestController {

    private final UserDbService userDbService;

    /**
     * Получить список всех пользователей
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return страница пользователей
     */
    @Operation(
            summary = "Получить список всех пользователей"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/getAllUsers/{pageNumber}/{pageSize}")
    public ResponseEntity<PageDTO> getAllUsers(
            @PathVariable("pageNumber")
            @Parameter(description = "номер запрашиваемой страницы")
            Integer pageNumber,
            @PathVariable("pageSize")
            @Parameter(description = "размер страницы / количество объектов")
            Integer pageSize) {
        PageDTO usersPageDTO;
        try {
            usersPageDTO = userDbService.findAll(pageNumber, pageSize);
        } catch (RuntimeException e) {
            throw new AnyReasonException(e.getMessage());
        }
        return new ResponseEntity<>(usersPageDTO, HttpStatus.OK);
    }
}
