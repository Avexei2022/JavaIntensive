package ru.kolodin.model.habits;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.kolodin.model.users.User;

import java.util.Date;

/**
 * Класс привычки
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "habits")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Habit {
    /**
     * Уникальный идентификатор привычки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", unique = true)
    private Long id;

    /**
     * Название привычки
     */
    @Column(name = "title")
    private String title;

    /**
     * Описание привычки
     */
    @Column(name = "description")
    private String description;

    /**
     * Периодичность привычки
     */
    @Column(name = "frequency")
    private Frequency frequency;

    /**
     * Пользователь/обладатель привычки
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    /**
     * Дата создания привычки
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
}
